# Android apps
## Project that contains two android apps: MazeGames and OnlineShop

## Maze games Application

In this application, there are two separate games: Ball Maze and Random Maze. The main menu contains two buttons leading to each of the games. Upon pressing one of them, the view is switched to the corresponding game view. There is also a button to return to the main menu (located in the top left corner).

---

### Ball Maze

The game involves navigating the ball to avoid obstacles. The ball is controlled by reading data from the Accelerometer sensor built into the phone. It consists of:

- **MazeObject Class**: representing objects within the maze. It sets the current location of objects, determines the texture of the object, its type, boundaries, and position within the maze.
- **Maze Class**: representing the maze in the game. It draws the entire maze, stores bitmaps representing different types of maze elements (e.g., walls, floor), and also a two-dimensional array specifying the type of each maze cell.
- **DrawingView Class**: responsible for drawing and interacting with the game. It holds objects representing the ball, goal, and obstacles in the maze. It handles player interactions such as moving the ball based on accelerometer data and detecting collisions. It manages the initialization of the maze and game objects and draws them on the screen.
- **The MainActivity** class initializes the sensor and displays messages in case of loss and victory.

---

### Random Maze

The game simply involves navigating the maze. Control is handled by touch input. After completing the maze, it is randomly generated again. It consists of:

- **Cell Class**: representing a single cell in the maze. It has flags indicating the presence of walls on the top, bottom, left, and right sides of the cell, as well as information about visitation and column and row indices.
- **GameView Class**: responsible for randomly generating and displaying the maze and interacting with the user. It creates the maze, stores information about the player's position and the goal, and handles drawing walls, player, and goal on the screen. It also allows controlling the player using gestures on the touchscreen.

Random maze generation is based on the "Recursive backtracking" algorithm.

---

## Online Shop Application

This application showcases the view of an online shop, but it is not functional. The application allows navigation between multiple views (profile, cart), adding items to and removing them from the cart, scrolling the application, and selecting items for purchase.


###  Layouts

The application includes the following layouts:

- **activity_main:** Main view of the application.
- **activity_cart:** Shopping cart view.
- **activity_detail:** Detailed product view.
- **activity_profile:** User profile view.
- **viewholder_cart:** View for a product appearing in the shopping cart.
- **viewholder_pup_list:** View of a product appearing in the "Popular" tab on the main screen.

### Architecture

The application utilizes ActivityBinding, a part of the Android Jetpack architecture, allowing for seamless binding between views and application code. ActivityBinding generates bindings between XML layouts and Activity code. The architecture consists of:

- **CartActivity Class:** Manages the display of the shopping cart, calculates purchase costs, and handles user interactions.
- **DetailActivity Class:** Responsible for displaying details of a selected product in the online store and adding it to the cart.
- **ProfileActivity Class:** Displays the user's profile.
- **PopularDomain Class:** A model class representing data of popular products in the online store.
- **CartAdapter Class:** A RecyclerView adapter that handles displaying shopping cart items in a list.
- **PopularAdapter Class:** A RecyclerView adapter that handles displaying popular products in a list.
- **MainActivity Class:** Responsible for generating the entire view and switching views based on the clicked option.

The adapter classes are responsible for displaying shopping cart items in the user interface. Each element is represented by a ViewHolder, which is linked to the XML layout using View Binding.

