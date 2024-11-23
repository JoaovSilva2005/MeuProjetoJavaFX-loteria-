package com.example.meuprojetojavafx.model;

import java.time.LocalDate;

public class Usuario {

    private String usuario;       // Considerando que "usuario" é o nome do usuário
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String tipoUsuario;   // "admin" ou "usuario"

    // Construtor
    public Usuario(String usuario, String cpf, String email, String senha, LocalDate dataNascimento, String tipoUsuario) {
        this.usuario = usuario;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters e Setters
    public String getNome() {  // Alterado para getNome(), como você precisa
        return usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return usuario + "," + cpf + "," + email + "," + senha + "," + dataNascimento + "," + tipoUsuario;
    }
}
