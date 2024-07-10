package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.FilmeRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.SerieRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.UsuarioRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class TestUtils {

    //Filmes TesteUtils

    public static FilmeRequestDTO filmeRequestDTO(){
        return FilmeRequestDTO.builder()
                .nome("Filme_testeUnitário")
                .anoLancamento(2020)
                .diretor("Diretor_testeUnitário")
                .build();
    }

    public static FilmeRequestDTO filmeRequestDTOForUpdate(){
        return FilmeRequestDTO.builder()
                .nome("Filme_update")
                .build();
    }



    public static Filme filme(){
        return Filme.builder()
                .id(5)
                .nome(filmeRequestDTO().getNome())
                .anoLancamento(filmeRequestDTO().getAnoLancamento())
                .diretor(filmeRequestDTO().getDiretor())
                .dataDeInclusão(LocalDateTime.now())
                .tipo(TipoEntretenimento.FILME)
                .build();
    }

    public static List<Filme> listFilmes(){
        return List.of(filme(), Filme.builder()
                .id(6)
                .nome("Teste_Filme_Lista")
                .anoLancamento(2000)
                .diretor("Diretor_filme_Lista")
                .dataDeInclusão(LocalDateTime.now())
                .tipo(TipoEntretenimento.FILME)
                .build());
    }

    // Series TesteUltils
    public static Serie serie(){
        return Serie.builder()
                .id(1)
                .nome("Serie_TestUnitario")
                .anoLancamento(2003)
                .numeroEpisodios(50)
                .numeroTemporadas(3)
                .dataDeInclusão(LocalDateTime.now())
                .tipo(TipoEntretenimento.SERIE)
                .build();
    }

    public static SerieRequestDTO serieRequestDTO(){
        return SerieRequestDTO.builder()
                .nome(serie().getNome())
                .anoLancamento(serie().getAnoLancamento())
                .numeroTemporadas(serie().getNumeroTemporadas())
                .numeroEpisodios(serie().getNumeroEpisodios())
                .build();
    }

    public static List<Serie> listSeries(){
        return List.of(serie(),
                Serie.builder()
                        .id(2)
                        .nome("Serie_TestUnitario2")
                        .anoLancamento(2004)
                        .numeroEpisodios(55)
                        .numeroTemporadas(2)
                        .dataDeInclusão(LocalDateTime.now())
                        .tipo(TipoEntretenimento.SERIE)
                        .build());
    }

    public static SerieRequestDTO serieRequestDTOUpdate(){
        return SerieRequestDTO.builder()
                .nome("Serie_update")
                .build();
    }

    // Grupo TestUtils

    public static Grupo grupo(){
        return Grupo.builder()
                .nome("USER")
                .build();

    }
    public static List<String> grupoString(){
        return List.of("USER");

    }

    public static List<String> listGroupoString(){
        return List.of("ADM", "USER");
    }

    public static List<Grupo> listGroupo(){
        return List.of(grupo(),
                Grupo.builder()
                        .nome("USER")
                        .build());
    }

    // Usuario TestUtils

    public static Usuario usuario(){
        return Usuario.builder()
                .nome("Usuario_teste")
                .login("usuarioLogin")
                .senha("SenhaTeste")
                .build();

    }

    public static UsuarioRequestDTO usuarioRequestDTO(){
        return UsuarioRequestDTO.builder()
                .usuario(usuario())
                .grupos(grupoString())
                .build();
    }

    public static UsuarioResponseDTO usuarioResponseDTO(){
        return UsuarioResponseDTO.builder()
                .nome("Usuario_teste")
                .login("usuarioLogin")
                .grupos(grupoString())
                .build();
    }
    public static List<Usuario> listaUsuario(){
        return List.of(usuario(),
                Usuario.builder()
                .nome("Usuario_teste2")
                .login("usuarioLogin2")
                .senha("SenhaTeste2")
                .build());

    }

    public static List<UsuarioResponseDTO> usuarioResponseDTOList(){
        return List.of(usuarioResponseDTO1()
                , usuarioResponseDTO2());
    }

    public static UsuarioResponseDTO usuarioResponseDTO1(){
        return UsuarioResponseDTO.builder()
                .nome(usuario().getNome())
                .login(usuario().getLogin())
                .build();
    }

    public static UsuarioResponseDTO usuarioResponseDTO2(){
        return UsuarioResponseDTO.builder()
                .nome(listaUsuario().get(1).getNome())
                .login(listaUsuario().get(1).getLogin())
                .build();
    }


}
