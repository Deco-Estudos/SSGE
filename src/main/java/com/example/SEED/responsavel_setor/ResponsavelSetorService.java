package com.example.SEED.responsavel_setor;

import com.example.SEED.Combo.Combo;
import com.example.SEED.Combo.ComboDTO;
import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaStatus;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResponsavelSetorService {

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional(readOnly = true)
    public List<ComboDTO> listarCombos() {


        String usuarioEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        Usuario usuarioLogado = userRepository.findUsuarioByEmail(usuarioEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioEmail));


        Set<Setor> setoresDoUsuario = usuarioLogado.getSetores();


        if (setoresDoUsuario == null || setoresDoUsuario.isEmpty()) {
            return new ArrayList<>();
        }


        List<ComboDestino> destinos = comboDestinoRepository.findBySetorIn(setoresDoUsuario);

        LocalDateTime agora = LocalDateTime.now();

        return destinos.stream()

                .filter(destino -> {
                    Competencia c = destino.getCompetencia();
                    return c != null &&
                            c.getCompetenciaStatus() == CompetenciaStatus.ABERTO &&
                            agora.isAfter(c.getDataInicio()) &&
                            agora.isBefore(c.getDataFim());
                })
                .map(ComboDestino::getCombo)
                .distinct()

                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    private ComboDTO toDTO(Combo combo) {
        return new ComboDTO(
                combo.getId(),
                combo.getNomeCombo(),
                combo.getDescricao(),
                combo.isAtivo(),
                combo.getDataCriacao()
        );
    }
}