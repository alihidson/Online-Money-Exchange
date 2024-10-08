package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.regex.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.Border;

public class LoginSignupPage extends Application {

    public static String UserName, PassWord;

    private TextField firstNameField, lastNameField, AgeField;
    private TextField usernameField, newUsernameField, emailField, phoneNumber, captchaField;

    private TextField newPasswordField, newPasswordAgain;
    private PasswordField passwordField;

    public static Database database;
    private int captchaCode;
    private Label captchaCodeLabel;
    public static String firstNameProf, lastNameProf, AgeProf, phoneNumberProf, emailProf;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login / Sign Up");

        String imageIcon = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/icon.png";
        Image icon = new Image(imageIcon);
        primaryStage.getIcons().add(icon);

        Text text = new Text();
        text.setText("Welcome to Money Exchange");

        String WelcomeFont = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/Font/Sectar.otf";
        text.setFont(Font.font(WelcomeFont, 25));
        text.setFill(Color.rgb(255,0,0));


        String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/background.jpg";
        Image backgroundImage = new Image(imagePath);


        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );


        primaryStage.setResizable(false);

//        primaryStage.setFullScreen(true);


        database = new Database();

        // Login form
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        Label captchaLabel = new Label("Captcha:");
        captchaField = new TextField();
        captchaField.setPromptText("Enter Captcha");
        captchaField.setMaxWidth(100);

        captchaCode = generateRandomNumber();
        captchaCodeLabel = new Label(String.valueOf(captchaCode));
        captchaCodeLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        captchaCodeLabel.setPadding(new Insets(5));
        captchaCodeLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        captchaCodeLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Button refreshCaptchaButton = new Button("Refresh");
        refreshCaptchaButton.setOnAction(e -> {
            captchaCode = generateRandomNumber();
            captchaCodeLabel.setText(String.valueOf(captchaCode));
        });

        HBox captchaBox = new HBox(5, captchaCodeLabel, refreshCaptchaButton);

        BorderPane captchaPane = new BorderPane();
        captchaPane.setLeft(captchaBox);
        captchaPane.setBottom(captchaField);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: rgb(76, 175, 80); -fx-text-fill: white; -fx-font-size: 16px;");
        loginButton.setOnAction(e -> login(primaryStage));


        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password?");
        forgotPasswordLink.setOnAction(e -> forgotPassword());


        VBox loginVBox = new VBox(10);
        loginVBox.getChildren().addAll(text, loginLabel, usernameField, passwordField, captchaLabel, captchaPane, loginButton, forgotPasswordLink);
        loginVBox.setPadding(new Insets(10));
        //loginVBox.setBackground(new Background(background));
        loginVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,171,255), CornerRadii.EMPTY, Insets.EMPTY)));
        loginVBox.setBorder(new Border(new BorderStroke(Color.rgb(141, 8, 79),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));


        // Sign up form
        Label signupLabel = new Label("Sign Up");
        signupLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");
        firstNameField.setMaxWidth(200);

        lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");
        lastNameField.setMaxWidth(200);

        AgeField = new TextField();
        AgeField.setPromptText("Enter your Age");
        AgeField.setMaxWidth(200);

        newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");
        newUsernameField.setMaxWidth(200);

        newPasswordField = new TextField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setMaxWidth(200);


        newPasswordAgain = new TextField();
        newPasswordAgain.setPromptText("New Password Again");
        newPasswordAgain.setMaxWidth(200);


        phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone Number");
        phoneNumber.setMaxWidth(200);


        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);


        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: rgb(0, 140, 186); -fx-text-fill: white; -fx-font-size: 16px;");
        signupButton.setOnAction(e -> signup());
        VBox signupVBox = new VBox(10);
        signupVBox.getChildren().addAll(signupLabel, newUsernameField ,firstNameField, lastNameField, AgeField, newPasswordField, newPasswordAgain, phoneNumber,emailField, signupButton);
        signupVBox.setPadding(new Insets(10));
        signupVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,171,255), CornerRadii.EMPTY, Insets.EMPTY)));
        signupVBox.setBorder(new Border(new BorderStroke(Color.rgb(141, 8, 79),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        signupVBox.setVisible(false);

        // Toggle Button
        Button toggleButton = new Button("Don't have an account? Sign Up");
        toggleButton.setStyle("-fx-background-color: rgb(244, 67, 54); -fx-text-fill: white; -fx-font-size: 14px;");
        toggleButton.setOnAction(e -> {
            loginVBox.setVisible(!loginVBox.isVisible());
            signupVBox.setVisible(!signupVBox.isVisible());

            if (loginVBox.isVisible()) {
                toggleButton.setText("Don't have an account? Sign Up");
            }
            else {
                toggleButton.setText("Already have an account? Login");
            }
        });


        StackPane root = new StackPane();
        root.getChildren().addAll(loginVBox, signupVBox, toggleButton);
        // top-right-bottom-left
        StackPane.setMargin(loginVBox, new Insets(150, 250, 150, 250));
        StackPane.setMargin(signupVBox, new Insets(150, 250, 150, 250));
        StackPane.setMargin(toggleButton, new Insets(350, 0, 0, 0));
        //root.setBackground(new Background(new BackgroundFill(Color.rgb(141, 8, 79), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setBackground(new Background(background));
        Scene scene = new Scene(root, 1280, 740);
        primaryStage.setScene(scene);



        String buttonIdleStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px;";
        String buttonHoverStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 2px;";

        String loginButtonIdleColor = "rgb(69, 160, 73)";
        String loginButtonHoverColor = "rgb(69, 160, 73)";
        String signupButtonIdleColor = "rgb(0, 123, 156)";
        String signupButtonHoverColor = "rgb(0, 140, 186)";

        loginButton.setOnMouseEntered(e -> loginButton.setStyle(String.format(buttonHoverStyle, loginButtonHoverColor)));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(String.format(buttonIdleStyle, loginButtonIdleColor)));
        signupButton.setOnMouseEntered(e -> signupButton.setStyle(String.format(buttonHoverStyle, signupButtonHoverColor)));
        signupButton.setOnMouseExited(e -> signupButton.setStyle(String.format(buttonIdleStyle, signupButtonIdleColor)));

        //applyButtonHoverEffect(toggleButton, String.format(buttonIdleStyle, "rgb(244, 67, 54)"), String.format(buttonHoverStyle, "rgb(244, 67, 54)"));

        primaryStage.show();
    }

    private void login(Stage primaryStage) {
        UserName = usernameField.getText();
        PassWord = passwordField.getText();
        int captcha = Integer.parseInt(captchaField.getText());

        if (database.validateUser(UserName, PassWord) && captcha == captchaCode) {
            System.out.println("Login Successful");

            database.getUserInfo(UserName, firstNameField, lastNameField, AgeField, phoneNumber, emailField);

            primaryStage.close();
            Stage HomeStage = new Stage();
            HomePage homePage = new HomePage();
            homePage.start(HomeStage);
        }
        else if(Integer.parseInt(captchaField.getText()) != captchaCode) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("Invalid Captcha, please try again");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Wrong");
            alert.setHeaderText("Username not found");
            alert.setContentText("The username was not found in database.");
            alert.showAndWait();
        }

    }



    private void signup() {

        int sw = 1;


//        if(database.isUsernameAvailable(usernameField.getText())) {
//            Alert alert = new Alert(AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Sorry");
//            alert.setContentText("This Username has Available, please write another username");
//            sw = 0;
//            alert.showAndWait();
//        }
        if(firstNameField.getText() == null || lastNameField.getText() == null || AgeField == null
                || newUsernameField.getText() == null || newPasswordField.getText() == null
                || emailField.getText() == null || newPasswordAgain.getText() == null
                || phoneNumber.getText() == null) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("No field should be empty, please try again");
            sw = 0;
            alert.showAndWait();

        }

        else if(!isValidEmail(emailField.getText())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("This Email is not valid, please try again");
            sw = 0;
            alert.showAndWait();

        }

        else if(!isPasswordValid(newPasswordField.getText())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("This Password is not valid, please try again");
            sw = 0;
            alert.showAndWait();
        }

        else {
            boolean flag = true;
            for(int i=0; i< newPasswordField.getText().length() && i< newPasswordAgain.getText().length() && flag; i++) {
                if(newPasswordField.getText().charAt(i) != newPasswordAgain.getText().charAt(i)) {
                    flag = false;
                }

                if(!flag) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Sorry");
                    alert.setContentText("This new Password is not equal by again Password, please try again");
                    sw = 0;
                    alert.showAndWait();
                }


            }
        }



        if(sw == 1) {

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String Age = AgeField.getText();
            String newUsername = newUsernameField.getText();
            String newPassword = newPasswordField.getText();
            String newEmail = emailField.getText();
            String newPhoneNumber = phoneNumber.getText();

            database.addUser(newUsername, firstName, lastName, Age, newPassword, newEmail, newPhoneNumber);

            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Sign Up Successful");
            alert.setHeaderText("You Successfully create Account");
            alert.setContentText("Now you can Login to your Account by Login Page");

        }
//        else {
//            Alert alert = new Alert(AlertType.ERROR);
//            alert.setTitle("Wrong");
//            alert.setHeaderText("Wrong Sign Up");
//            alert.setContentText("Sorry Something happened");
//            alert.showAndWait();
//        }
    }

    private int generateRandomNumber() {
        Random rnd = new Random();
        return rnd.nextInt(9000) + 1000; // Generates a random number between 1000 and 9999
    }

    private void forgotPassword() {
        String username = usernameField.getText();
        String newPassword = generateRandomPassword();
        String email = database.getUserEmail(username);

        if (email != null) {
            // Update database with new password
            database.updatePassword(username, newPassword);

            // Send email
            //sendPasswordResetEmail(email, newPassword);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Password Reset");
            alert.setHeaderText("New Password Sent");
            alert.setContentText("A new password has been sent to your email address.");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Password Reset");
            alert.setHeaderText("Username not found");
            alert.setContentText("The username was not found in database.");
            alert.showAndWait();
        }
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 10; i++) {
            newPassword.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return newPassword.toString();
    }

    private void sendPasswordResetEmail(String email, String newPassword) {
        final String username = "exchangeakhavanandjafarzadeh@gmail.com"; // email address
        final String password = "Aa1357924680Sj"; // password of that email

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset");
            message.setText("Dear User,\n\n"
                    + "Your password has been reset. Your new password is: " + newPassword + "\n\n"
                    + "Please login with this new password and change it as soon as possible.");

            Transport.send(message);

            System.out.println("Password reset email sent to: " + email);

        }
        catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send password reset email.");
        }
    }


    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$";
        Pattern patternPassword = Pattern.compile(regex);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherPassword.find();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern patternEmail = Pattern.compile(regex);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }

    public static void main(String[] args) {
        launch(args);
    }
}