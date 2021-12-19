package com.example.uts_pbp_d_kelompok_3.ui.auth;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;

    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getEmail()).thenReturn("");
        System.out.println("Email : " + view.getEmail());
        presenter.onLoginClicked();
        verify(view).showEmailError("Email tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getPassword()).thenReturn("");
        System.out.println("Password : " + view.getPassword());
        presenter.onLoginClicked();
        verify(view).showPasswordError("Password tidak boleh kosong");
    }

    @Test
    public void shouldStartMainActivityWhenNimAndPasswordAreCorrect() throws Exception {
        when(view.getEmail()).thenReturn("praktikumweb8@gmail.com");
        System.out.println("Email : " + view.getEmail());
        when(view.getPassword()).thenReturn("praktikumweb8");
        System.out.println("Password : " + view.getPassword());
        when(service.getValid(view, view.getEmail(), view.getPassword())).thenReturn(true);
        System.out.println("Hasil : " + service.getValid(view, view.getEmail(), view.getPassword()));
        presenter.onLoginClicked();
    }
}