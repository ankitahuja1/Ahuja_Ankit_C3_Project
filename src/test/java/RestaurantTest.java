import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;

    Restaurant spyRestaurant;

    @BeforeEach
    public void openingTimeArrange(){
        openingTime = LocalTime.parse("10:00:00");
        closingTime = LocalTime.parse("21:30:00");
        restaurant = new RestaurantService().addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        spyRestaurant = Mockito.spy(restaurant);
    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //date within range
        LocalTime dateWithinRange = LocalTime.of(11,12,34);
        //edge case opening date
        LocalTime dateOpeningEdge = LocalTime.of(10,00,00);

        //mock current time
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(dateWithinRange, dateOpeningEdge);

        //assert true
        assertTrue(spyRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //date outside range before time
        LocalTime dateOutsideRangeBefore = LocalTime.of(9,12,02);
        //date outside range after time
        LocalTime dateOutsideRangeAfter = LocalTime.of(22,32,42);
        //edge case closing date
        LocalTime dateClosingDate = LocalTime.of(21,30,00);

        //mock current time
        Mockito.when(spyRestaurant.getCurrentTime()).thenReturn(dateOutsideRangeAfter, dateOutsideRangeAfter, dateClosingDate);

        //assert false
        assertFalse(spyRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<ORDER-TOTAL>>>>>>>>>>>>>>>>>>>>>>

        @Test
        public void selected_items_should_return_total_value(){
            List<String> selectedItems = new ArrayList<String>();
            selectedItems.add("Sweet corn soup");
            selectedItems.add("Vegetable lasagne");
            assertEquals(119+269,restaurant.getOrderTotal(selectedItems));
        }

        @Test
        public void nothing_selected_should_return_total_zero(){
            List<String> selectedItems = new ArrayList<String>();
            assertEquals(0,restaurant.getOrderTotal(selectedItems));
        }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER-TOTAL>>>>>>>>>>>>>>>>>>>>>>
}