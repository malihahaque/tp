package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.household.Household;

/**
 * Panel containing the list of households.
 */
public class HouseholdListPanel extends UiPart<Region> {
    private static final String FXML = "HouseholdListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(HouseholdListPanel.class);

    @FXML
    private ListView<Household> householdListView;

    /**
     * Constructs a {@code HouseholdListPanel} with the given list of households.
     *
     * <p>This constructor initializes the panel with the provided {@code ObservableList} of {@code Household} objects.
     * It sets up the list view and its cell factory to display each household using the
     * {@code HouseholdListViewCell}.</p>
     *
     * @param householdList The list of households to be displayed in the panel.
     */
    public HouseholdListPanel(ObservableList<Household> householdList, SessionListPanel sessionListPanel) {
        super(FXML);
        assert householdList != null : "Household list must not be null";
        assert sessionListPanel != null : "SessionListPanel must not be null";
        householdListView.setItems(householdList);
        householdListView.setCellFactory(listView -> new HouseholdListViewCell());

        // Add selection listener to control the visibility of the addSessionButton
        householdListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        sessionListPanel.showAddSessionButton(true);
                    } else {
                        sessionListPanel.showAddSessionButton(false);
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Household} using a {@code HouseholdCard}.
     */
    class HouseholdListViewCell extends ListCell<Household> {
        @Override
        protected void updateItem(Household household, boolean empty) {
            super.updateItem(household, empty);

            if (empty || household == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-background-color: white");
            } else {
                setGraphic(new HouseholdCard(household, getIndex() + 1).getRoot());
                if (getIndex() % 2 == 0) {
                    setStyle("-fx-background-color: #FFF5E1;");
                } else {
                    setStyle("-fx-background-color: #FFFFFF;");
                }
            }
        }
    }

    /**
     * Programmatically selects the household in the list with the specified household id.
     * @param householdId the id of the household to select (e.g., "H000007")
     */
    public void selectHouseholdById(String householdId) {
        ObservableList<Household> households = householdListView.getItems();
        for (int i = 0; i < households.size(); i++) {
            Household household = households.get(i);
            if (household.getId().toString().equals(householdId)) {
                householdListView.getSelectionModel().clearAndSelect(i);
                householdListView.scrollTo(i);
                householdListView.getFocusModel().focus(i);
                break;
            }
        }
    }

    public ListView<Household> getListView() {
        return householdListView;
    }
}
