package com.example.SEED.Preenchimento;

import com.example.SEED.ComboDestino.ComboDestino;
import com.example.SEED.ComboDestino.ComboDestinoRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.Item.Item;
import com.example.SEED.Item.ItemRepository;
import com.example.SEED.Usuario.Usuario;
import com.example.SEED.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PreenchimentoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ComboDestinoRepository comboDestinoRepository;

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Transactional
    public void salvarPreenchimentos(Long comboDestinoId, List<PreenchimentoCreateDTO> preenchimentosDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuario = userRepository.findUsuarioByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        ComboDestino comboDestino = comboDestinoRepository.findById(comboDestinoId)
                .orElseThrow(() -> new EntityNotFoundException("Combo destino não encontrado"));

        EstruturaAdm estrutura = comboDestino.getEstruturaAdm();
        Competencia competencia = comboDestino.getCompetencia();

        for (PreenchimentoCreateDTO dto : preenchimentosDTO) {
            Item item = itemRepository.findById(dto.itemId())
                    .orElseThrow(() -> new EntityNotFoundException("Item não encontrado: " + dto.itemId()));

            Preenchimento p = Preenchimento.builder()
                    .item(item)
                    .usuario(usuario)
                    .estruturaAdm(estrutura)
                    .competencia(competencia)
                    .valor(dto.valor())
                    .dataPreenchimento(new Date())
                    .observacao(dto.observacao())
                    .build();

            preenchimentoRepository.save(p);
        }
    }
}