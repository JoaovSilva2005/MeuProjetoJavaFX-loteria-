package com.example.meuprojetojavafx.util;

import com.example.meuprojetojavafx.model.Usuario;

public class UsuarioLogado {

    // A variável que armazena o usuário logado
    private static Usuario usuarioLogado;

    // Método para obter o usuário logado
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // Método para setar o usuário logado
    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
}
