package controllers;


import models.AuthorisedUser;
import models.utils.AuthorisedUserUtils;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.authorization.signin;
import views.html.authorization.signup;
import views.html.authorization.summary;
import views.html.index;

import static play.data.Form.form;

public class Authorization extends Controller {

    /**
     * Sign in page.
     */
    public static Result signin() {
        Form<AuthorisedUser> signinForm = form(AuthorisedUser.class);
        return ok(signin.render(signinForm, "ok"));
    }
    public static Result quickSignin() {
        Form<AuthorisedUser> signinForm = form(AuthorisedUser.class);
        DynamicForm requestData = form().bindFromRequest();
        String email = requestData.get("email");
        String password = requestData.get("password");
        // Check if the email is valid
        if (requestData.hasErrors()) {

            return ok(signin.render(signinForm, "notFilled"));
        } else {
            AuthorisedUser user = AuthorisedUserUtils.getUser(email, password);

            if (user != null) {

                if (!Boolean.TRUE.equals(true)) {
                    return ok(signin.render(signinForm, "notAuthorized"));
                }
                session("connected", user.email);

                return redirect(routes.Application.getProfile(user.id));
            } else {
                return ok(signin.render(signinForm, "invalidUser"));
            }
            //return ok();
        }
    }
    /**
     * User login check out.
     */
    public static Result checkOut() {
        Form<AuthorisedUser> uf = form(AuthorisedUser.class).bindFromRequest();
        Form<AuthorisedUser> signinForm = form(AuthorisedUser.class);

        // Check if the email is valid
        if (uf.hasErrors()) {
            System.out.println(uf.errorsAsJson());
            return ok(signin.render(signinForm, "notFilled"));
        } else {
            AuthorisedUser u = uf.get();
            AuthorisedUser user = AuthorisedUserUtils.getUser(u.email, u.password);

            if (user != null) {

                if (!Boolean.TRUE.equals(true)) {
                    return ok(signin.render(signinForm, "notAuthorized"));
                }
                session("connected", user.email);

                return redirect(routes.Application.getProfile(user.id));
            } else {
                return ok(signin.render(signinForm, "invalidUser"));
            }
            //return ok();
        }
    }

    public static Result logOut() {
        Form<AuthorisedUser> signinForm = form(AuthorisedUser.class);
        session().remove("connected");

        return ok(index.render(""));
    }

    /**
     * Display a blank form.
     */
    public static Result blank() {
        Form<AuthorisedUser> signupForm = form(AuthorisedUser.class);

        return ok(signup.render(signupForm));
    }

    /**
     * Handle the form submission.
     */
    public static Result submit() {
        Form<AuthorisedUser> signupForm = form(AuthorisedUser.class);
        Form<AuthorisedUser> filledForm = signupForm.bindFromRequest();

        // Check accept conditions
        if (!"true".equals(filledForm.field("accept").value())) {
            filledForm.reject("accept", "You must accept the terms and conditions");
        }

        // Check repeated password
        if (!filledForm.field("password").valueOr("").isEmpty()) {
            if (!filledForm.field("password").valueOr("").equals(filledForm.field("repeatPassword").value())) {
                filledForm.reject("repeatPassword", "Password don't match");
            }
        }

        // Check if the login is valid
        if (!filledForm.hasErrors()) {
            if (filledForm.get().firstName.equals("admin") || filledForm.get().firstName.equals("guest")) {
                filledForm.reject("login", "This username is already taken");
            }
        }

        if (filledForm.hasErrors()) {
            return badRequest(signup.render(filledForm));
        } else {
            AuthorisedUser created = filledForm.get();

            AuthorisedUser newUser = new AuthorisedUser(created.firstName, created.lastName, created.middleName,
                    created.email, created.password);

            newUser.accepted = false;
            newUser.save();
            newUser.save();

            return ok(summary.render(newUser, signupForm));
        }
    }
}
