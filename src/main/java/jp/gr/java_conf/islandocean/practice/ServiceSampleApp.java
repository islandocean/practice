package jp.gr.java_conf.islandocean.practice;

import java.io.IOException;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ServiceSampleApp extends Application {

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {

		//
		// Service
		//

		MyService service = new MyService();

		service.setOnReady(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent ev) {
				System.out.println("### service.setOnReady handle()");
				printInfo(ev.getSource());
			}
		});

		service.setOnRunning(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent ev) {
				System.out.println("### service.setOnRunning handle()");
				printInfo(ev.getSource());
			}
		});

		service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent ev) {
				System.out.println("### service.setOnSucceeded handle()");
				printInfo(ev.getSource());
				service.reset();
			}
		});

		service.setOnCancelled(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent ev) {
				System.out.println("### service.setOnCancelled handle()");
				printInfo(ev.getSource());
				service.reset();
			}
		});

		service.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent ev) {
				System.out.println("### service.setOnFailed handle()");
				printInfo(ev.getSource());
				service.reset();
			}
		});

		//
		// GUI
		//

		// Scene
		Scene scene = new Scene(new Group());
		scene.setFill(Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();

		stage.setTitle("Service Sample");
		stage.setWidth(500);
		stage.setHeight(200);

		// Label
		Label label = new Label("Service Sample.");

		// Buttons
		Button buttonRunSucceed = new Button("Run(Succeed)");
		buttonRunSucceed.setOnAction((ActionEvent e) -> {
			try {
				sampleRunSucceed(service);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button buttonRunFail = new Button("Run(Fail)");
		buttonRunFail.setOnAction((ActionEvent e) -> {
			try {
				sampleRunFail(service);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		Button buttonStop = new Button("Stop");
		buttonStop.setOnAction((ActionEvent e) -> {
			service.cancel();
		});
		Button buttonInfo = new Button("Console Out");
		buttonInfo.setOnAction((ActionEvent e) -> {
			System.out.println("### Console out");
			printInfo(service);
		});
		HBox hBox = new HBox();
		hBox.getChildren().addAll(buttonRunSucceed, buttonRunFail, buttonStop,
				buttonInfo);
		hBox.setSpacing(30);
		hBox.setAlignment(Pos.CENTER);
		hBox.setPadding(new Insets(10, 0, 0, 10));

		HBox hBoxMessage = new HBox();
		hBoxMessage.setPadding(new Insets(10, 0, 0, 10));
		Label CaptionMessage = new Label("Message:");
		Label labelMessage = new Label();
		hBoxMessage.getChildren().addAll(CaptionMessage, labelMessage);
		labelMessage.textProperty().bind(service.messageProperty());

		HBox hBoxTitle = new HBox();
		hBoxTitle.setPadding(new Insets(10, 0, 0, 10));
		Label CaptionTitle = new Label("Title:");
		Label labelTitle = new Label();
		hBoxTitle.getChildren().addAll(CaptionTitle, labelTitle);
		labelTitle.textProperty().bind(service.titleProperty());

		ProgressBar bar = new ProgressBar();
		bar.setPadding(new Insets(10, 0, 0, 10));
		bar.progressProperty().bind(service.progressProperty());

		// Layout
		VBox vBox = new VBox();
		vBox.getChildren().add(label);
		vBox.getChildren().add(hBox);
		vBox.getChildren().add(hBoxMessage);
		vBox.getChildren().add(hBoxTitle);
		vBox.getChildren().add(bar);
		((Group) scene.getRoot()).getChildren().add(vBox);
	}

	private void sampleRunFail(MyService service) throws InterruptedException {
		service.setIdxFail(4);
		service.start();
	}

	private void sampleRunSucceed(MyService service)
			throws InterruptedException {
		service.setIdxFail(10);
		service.start();
	}

	private static class MyService extends Service<String> {
		private StringProperty url = new SimpleStringProperty();
		private int idxFail;

		public final void setUrl(String value) {
			url.set(value);
		}

		public final String getUrl() {
			return url.get();
		}

		public final StringProperty urlProperty() {
			return url;
		}

		public int getIdxFail() {
			return idxFail;
		}

		public void setIdxFail(int idxFail) {
			this.idxFail = idxFail;
		}

		@Override
		protected Task<String> createTask() {
			return new Task<String>() {
				@Override
				protected String call() throws IOException {

					for (int counter = 0; counter < 10; ++counter) {
						System.out.println("task counter=" + counter);
						try {
							Thread.sleep(1000L);
						} catch (InterruptedException e) {
						}

						if (isCancelled()) {
							// Cancel
							System.out
									.println("Task#call(): isCancelled() is true.");
							return null;
						} else if (counter >= idxFail) {
							// Fail
							throw new IOException();
						}

						updateMessage("messageeeeeeeeee" + counter);
						updateProgress(10d * (counter + 1), 100d);
						updateTitle("titleeeeeeeeeeee" + counter);
						updateValue("valueeeeeeeee" + counter);
					}

					// Succeed
					return "my result";
				}

				@Override
				protected void succeeded() {
					super.succeeded();
					updateMessage("Done!");
				}

				@Override
				protected void cancelled() {
					super.cancelled();
					updateMessage("Cancelled!");
					System.out.println("task Cancelled!");
				}

				@Override
				protected void failed() {
					super.failed();
					updateMessage("Failed!");
				}
			};
		}
	}

	private void printInfo(Worker worker) {
		System.out.println("getValue():" + worker.getValue());
		System.out.println("getMessage():" + worker.getMessage());
		System.out.println("getProgress():" + worker.getProgress());
		System.out.println("getTitle():" + worker.getTitle());
		System.out.println("getWorkDone():" + worker.getWorkDone());
		System.out.println("getTotalWork():" + worker.getTotalWork());
		System.out.println("getState():" + worker.getState());
		System.out.println("---------------------------------");
	}
}