package com.example.meuprojetojavafx.util;

import com.example.meuprojetojavafx.model.Usuario;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

public class UsuarioUtils {

    private static final String ARQUIVO_USUARIOS = "usuarios.txt";

    // Método para carregar todos os usuários do arquivo
    public static List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(ARQUIVO_USUARIOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 6) {  // Agora temos 6 campos
                    String usuario = dados[0];
                    String cpf = dados[1];
                    String email = dados[2];
                    String senha = dados[3];
                    LocalDate dataNascimento = LocalDate.parse(dados[4]);
                    String tipoUsuario = dados[5];  // Tipo do usuário
                    usuarios.add(new Usuario(usuario, cpf, email, senha, dataNascimento, tipoUsuario));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para salvar todos os usuários no arquivo
    public static void salvarUsuariosNoArquivo(List<Usuario> usuarios) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(ARQUIVO_USUARIOS))) {
            for (Usuario usuario : usuarios) {
                writer.write(usuario.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para salvar um novo usuário no arquivo
    public static void salvarUsuarioEmArquivo(Usuario usuario) {
        List<Usuario> usuarios = carregarUsuarios();
        usuarios.add(usuario);
        salvarUsuariosNoArquivo(usuarios);
    }

    // Método para verificar se o CPF já está cadastrado
    public static boolean isCpfDuplicado(String cpf) {
        List<Usuario> usuarios = carregarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    // Método para verificar se o e-mail já está cadastrado
    public static boolean isEmailDuplicado(String email) {
        List<Usuario> usuarios = carregarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    // Método para buscar um usuário pelo nome
    public static Usuario buscarUsuarioPorNome(String nome) {
        List<Usuario> usuarios = carregarUsuarios();
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }

    // Método para atualizar um usuário no arquivo
    public static void atualizarUsuarioNoArquivo(Usuario usuarioAtualizado) {
        List<Usuario> usuarios = carregarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getCpf().equals(usuarioAtualizado.getCpf())) {
                usuarios.set(i, usuarioAtualizado); // Substitui o usuário
                break;
            }
        }
        salvarUsuariosNoArquivo(usuarios);
    }

    // Método para remover um usuário do arquivo
    public static void removerUsuarioDoArquivo(Usuario usuario) {
        List<Usuario> usuarios = carregarUsuarios();
        usuarios.removeIf(u -> u.getCpf().equals(usuario.getCpf()));
        salvarUsuariosNoArquivo(usuarios);
    }
}
