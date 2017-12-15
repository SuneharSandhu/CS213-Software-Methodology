/**
 * @author Anil Tilve
 * @author Ayush Joshi
 */

package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;
import java.util.Optional;

import javafx.util.Callback;

import models.Song;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ListController {

	@FXML
	ListView<Song> listView;
	@FXML
	Button addButton, editButton, deleteButton, confirmButton, cancelButton;
	@FXML
	TextField nameField, artistField, albumField, yearField;

	private BufferedWriter writer;
	private Gson gson;
	private String mode;

	public void start(Stage mainStage) throws IOException {
		BufferedReader reader = null;

		try {
			gson = new GsonBuilder().setPrettyPrinting().create();
			reader = new BufferedReader(new FileReader("songs.json"));
			listView.setItems(FXCollections.observableArrayList(gson.fromJson(reader, Song[].class)));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} finally {
			reader.close();
		}

		FXCollections.sort(listView.getItems());

		listView.getSelectionModel().select(0);

		showSelectedSongDetails(listView.getSelectionModel().getSelectedItem());
		listView.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue,
				newValue) -> showSelectedSongDetails(listView.getSelectionModel().getSelectedItem()));

		this.disableUserInput(true);
		this.mode = "";
		int currentYear = Year.now().getValue();

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				disableUserInput(false);
				mode = "Add";
			}
		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (listView.getItems().isEmpty())
					return;

				Song selectedSong = listView.getSelectionModel().getSelectedItem();

				Alert confirmation = generateConfirmation("Delete",
						"delete \"" + selectedSong.getName() + "\" - " + selectedSong.getArtist());

				Optional<ButtonType> result = confirmation.showAndWait();
				if (result.get() == ButtonType.YES) {

					int selectedIndex = listView.getSelectionModel().getSelectedIndex();

					if (selectedIndex < listView.getItems().size() - 1)
						listView.getSelectionModel().select(selectedIndex + 1);

					listView.getItems().remove(selectedIndex);

					try {
						writer = new BufferedWriter(new FileWriter("songs.json"));
						gson.toJson(listView.getItems().toArray(new Song[listView.getItems().size()]), writer);
					} catch (FileNotFoundException exception) {
						exception.printStackTrace();
					} catch (IOException exception) {
						exception.printStackTrace();
					} finally {
						try {
							writer.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
				}

				confirmation.close();
			}
		});

		editButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (listView.getItems().isEmpty())
					return;

				disableUserInput(false);
				mode = "Edit";

				Song selectedSong = listView.getSelectionModel().getSelectedItem();

				nameField.setText(selectedSong.getName());
				artistField.setText(selectedSong.getArtist());
				albumField.setText(selectedSong.getAlbum());
				yearField.setText(selectedSong.getYear());
			}
		});

		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Alert confirmation = generateConfirmation("Cancel", "cancel your changes");

				Optional<ButtonType> result = confirmation.showAndWait();

				if (result.get() == ButtonType.YES) {
					clearTextFields();
					disableUserInput(true);
				}

				confirmation.close();
			}
		});

		confirmButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String enteredName = nameField.getText(), enteredArtist = artistField.getText(),
						enteredYear = yearField.getText();

				if (enteredName.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Text Input Error");
					alert.setHeaderText("No Song Name Present Error");
					alert.setContentText("The song name is required.");

					alert.showAndWait();
					return;
				} else if (enteredArtist.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Text Input Error");
					alert.setHeaderText("No Artist Present error.");
					alert.setContentText("Artist is required.");

					alert.showAndWait();
					return;
				} else if(!enteredYear.isEmpty()){
					if (isValid(enteredYear) == false || Integer.parseInt(enteredYear.toString()) > currentYear || Integer.parseInt(enteredYear.toString())< 1890)
					{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Song " + mode + " Error");
						alert.setHeaderText("Error " + mode + "ing song");
						alert.setContentText("Please enter a year in the YYYY format from 1890 to "+ Integer.toString(currentYear) + '.');
						alert.showAndWait();
						return;
					}

				}

				Song selectedSong = listView.getSelectionModel().getSelectedItem();

				for (Song song : listView.getItems()) {
					if (mode.equals("Edit") && song.compareTo(selectedSong) == 0)
						continue;
					if (enteredName.toLowerCase().equals(song.getName().toLowerCase())
							&& enteredArtist.toLowerCase().equals(song.getArtist().toLowerCase())) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Song " + mode + " Error");
						alert.setHeaderText("Error " + mode + "ing song");
						alert.setContentText(
								"Another song with the same name and artist already exists in the library.");

						alert.showAndWait();
						return;
					}
				}

				String actionText;

				if (mode.equals("Edit"))
					actionText = "make these changes";
				else
					actionText = "add \"" + enteredName + "\" - " + enteredArtist + " to the library";

				Alert confirmation = generateConfirmation(mode, actionText);
				Optional<ButtonType> result = confirmation.showAndWait();

				if (result.get() == ButtonType.YES) {
					String enteredAlbum = albumField.getText();

					if (mode.equals("Add")) {
						Song newSong = new Song(enteredName, enteredArtist, enteredAlbum, enteredYear);
						listView.getItems().add(newSong);
						FXCollections.sort(listView.getItems());
						listView.getSelectionModel().select(newSong);
					} else {
						listView.getSelectionModel().getSelectedItem().setName(enteredName);
						listView.getSelectionModel().getSelectedItem().setArtist(enteredArtist);
						listView.getSelectionModel().getSelectedItem().setAlbum(enteredAlbum);
						listView.getSelectionModel().getSelectedItem().setYear(enteredYear);
						listView.refresh();
					}

					BufferedWriter writer = null;

					try {
						writer = new BufferedWriter(new FileWriter("songs.json"));
						Gson gson = new GsonBuilder().setPrettyPrinting().create();
						gson.toJson(listView.getItems().toArray(new Song[listView.getItems().size()]), writer);
					} catch (FileNotFoundException exception) {
						exception.printStackTrace();
					} catch (IOException exception) {
						exception.printStackTrace();
					} finally {
						try {
							writer.close();
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}
				}
				clearTextFields();
				disableUserInput(true);
				confirmation.close();
			}
		});
	}

	private void showSelectedSongDetails(Song selectedSong) {
		listView.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>() {

			public ListCell<Song> call(ListView<Song> param) {
				final ListCell<Song> cell = new ListCell<Song>() {
					@Override
					public void updateItem(Song item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							if (item.equals(selectedSong)) {
								setText(item.getName() + " - " + item.getArtist() + "\r" + item.getAlbum() + "\t\t"
										+ item.getYear());
							} else
								setText(item.toString());
						}
					}
				};
				return cell;
			}
		});
	}

	private void disableUserInput(boolean value) {
		nameField.setDisable(value);
		artistField.setDisable(value);
		albumField.setDisable(value);
		yearField.setDisable(value);
		confirmButton.setDisable(value);
		cancelButton.setDisable(value);

		addButton.setDisable(!value);
		deleteButton.setDisable(!value);
		editButton.setDisable(!value);
	}

	private void clearTextFields() {
		nameField.clear();
		artistField.clear();
		albumField.clear();
		yearField.clear();
	}

	private Alert generateConfirmation(String actionTitle, String actionText) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		confirmation.setTitle(actionTitle + " Confirmation Dialog");
		confirmation.setHeaderText("Confirm " + actionTitle);
		confirmation.setContentText("Are you sure you want to " + actionText + "?");

		confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		return confirmation;
	}

	public boolean isValid(String input)
	{
		try
		{
			for(int i =0;i<input.length();i++)
			{
				if(Character.isDigit(input.charAt(i))){
					continue;
				}
				else
				{
					return false;
				}
			}
			return true;
		}
		catch( Exception e)
		{
			return false;
		}
	}
}
