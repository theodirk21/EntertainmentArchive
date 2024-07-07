package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception.NotFoundException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.UsuarioGrupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.GrupoRepository;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.UsuarioGrupoRepository;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;

    private final GrupoRepository grupoRepository;

    private final UsuarioGrupoRepository usuarioGrupoRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;


    public UsuarioResponseDTO create(Usuario usuario, List<String> grupos) {
        String senhaEncoded = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoded);
        usuarioRepository.save(usuario);
        List<UsuarioGrupo> usuarioGrupoLista = grupos.stream().map(nomeGrupo ->
        {
            Optional<Grupo> grupoByName = grupoRepository.findByNome(nomeGrupo);
            if (grupoByName.isPresent()) {
                Grupo grupo = grupoByName.get();
                return getUsuarioGrupo(usuario, grupo);
            }
            return null;
        }).filter(grupo -> grupo != null).toList();
        UsuarioResponseDTO usuarioResponseDTO = mapper.map(usuario, UsuarioResponseDTO.class);

        usuarioResponseDTO.setGrupos(grupos);
        return usuarioResponseDTO;
    }

    private UsuarioGrupo getUsuarioGrupo(Usuario usuario, Grupo grupo) {
       var usuarioGrupo = new UsuarioGrupo(usuario, grupo);
        usuarioGrupoRepository.save(usuarioGrupo);
        return usuarioGrupo ;
    }

    public Usuario obterUsuarioEGrupo(String login){
        Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("Usuario não encontrado"));
        List<String> grupoByUsuario = usuarioGrupoRepository.findGrupoByUsuario(usuario);
        usuario.setGrupos(grupoByUsuario);

        return usuario;
    }

    public List<UsuarioResponseDTO> listaUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        List<UsuarioResponseDTO> responseDTOS = listaUsuarios.stream().map(usuario -> {
            List<String> grupos = usuarioGrupoRepository.findGrupoByUsuario(usuario);
            UsuarioResponseDTO usuarioResponseDTO = mapper.map(usuario, UsuarioResponseDTO.class);
            usuarioResponseDTO.setGrupos(grupos);

            return usuarioResponseDTO;
        }).collect(Collectors.toList());

        return responseDTOS;
    }


}
