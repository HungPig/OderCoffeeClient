package Order.Modal.System;

import Order.Modal.Auth.LoginForm;
import Order.Modal.forms.FormDashboard;
import Order.Modal.utils.UndoRedo;
import Order.Modal.dashboard.About;
import raven.modal.Drawer;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;


import javax.swing.*;

public class FormManager {

    protected static final UndoRedo<Form> FORMS = new UndoRedo<>();
    private static JFrame frame;
    private static MainForm mainForm;
    private static LoginForm login;

    public static void install(JFrame f) {
        frame = f;
        install();
        logout();
    }

    private static void install() {
        FormSearch.getInstance().installKeyMap(getMainForm());
    }

    public static void showForm(Form form) {
        if (form != FORMS.getCurrent()) {
            FORMS.add(form);
            form.formCheck();
            form.formOpen();
            mainForm.setForm(form);
            mainForm.refresh();
        }
    }

    public static void undo() {
        if (FORMS.isUndoAble()) {
            Form form = FORMS.undo();
            form.formCheck();
            form.formOpen();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    public static void redo() {
        if (FORMS.isRedoAble()) {
            Form form = FORMS.redo();
            form.formCheck();
            form.formOpen();
            mainForm.setForm(form);
            Drawer.setSelectedItemClass(form.getClass());
        }
    }

    public static void refresh() {
        if (FORMS.getCurrent() != null) {
            FORMS.getCurrent().formRefresh();
            mainForm.refresh();
        }
    }

    public static void login() {
        Drawer.setVisible(true);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(getMainForm());
        Drawer.setSelectedItemClass(FormDashboard.class);
        frame.repaint();
        frame.revalidate();
    }

    public static void logout() {
        Drawer.setVisible(false);
        frame.getContentPane().removeAll();
        LoginForm form = getLogin();
        form.formCheck();
        frame.getContentPane().add(login);
        FORMS.clear();
        frame.repaint();
        frame.revalidate();
    }

    public static JFrame getFrame() {
        return frame;
    }

    private static MainForm getMainForm() {
        if (mainForm == null) {
            mainForm = new MainForm();
        }
        return mainForm;
    }

    private static LoginForm getLogin() {
        if (login == null) {
            login = new LoginForm();
        }
        return login;
    }
    public static void showAbout() {
        ModalDialog.showModal(frame, new SimpleModalBorder(new About(), "About"),
                ModalDialog.createOption().setAnimationEnabled(false)
        );
    }
}
