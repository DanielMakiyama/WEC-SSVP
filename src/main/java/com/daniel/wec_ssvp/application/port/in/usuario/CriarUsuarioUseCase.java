package com.daniel.wec_ssvp.application.port.in.usuario;

import com.daniel.wec_ssvp.domain.model.TipoUsuario;
import com.daniel.wec_ssvp.domain.model.Usuario;

/** Porta de entrada: cadastrar um usuário com senha criptografada. */
public interface CriarUsuarioUseCase {

    Usuario executar(Command command);

    record Command(String email, String senha, TipoUsuario tipoUsuario) {}
}
