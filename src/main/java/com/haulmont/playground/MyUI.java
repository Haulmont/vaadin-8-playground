package com.haulmont.playground;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

@Theme("mytheme")
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();

        TextField name = new TextField();
        name.setCaption("Type your name here:");

        User user = new User();
        user.setCount(1);

        new Binder<>(User.class)
                .forField(name)
                .withConverter(new StringToIntegerConverter(0, "Unable to convert value"))
                .withValidator(new IntegerRangeValidator("Not in range", 1, 2))
                .bind("count")
                .read(user);

        Button button = new Button("Click Me");
        button.addClickListener(e ->
                layout.addComponent(new Label("Thanks " + name.getValue() + ", it works!"))
        );

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.setItems(
                new User("Peter", 1),
                new User("Ivan", 2),
                new User("Olga", 3)
        );
        userComboBox.setValue(new User("Mike", 4));
        userComboBox.setItemCaptionGenerator(User::getName);

        layout.addComponents(
                name,
                button,
                userComboBox
        );

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}