package com.example.SEED.ComboDestino;


import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaRepository;
import com.example.SEED.Combo.Combo;

import com.example.SEED.Combo.ComboRepository;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import com.example.SEED.Setor.Setor;
import com.example.SEED.Setor.SetorRepository;
import com.example.SEED.Setor.SetorService;
import com.example.SEED.Competencia.CompetenciaStatus;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class ComboDestinoService {

    @Autowired
    CompetenciaRepository competenciaRepository;

    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private EstruturaAdmRepository estruturaRepository;

    @Autowired
    private SetorRepository setorRepository;

    @Autowired
    private ComboDestinoRepository destinoRepository;

    @Autowired
    private SetorService setorService;

    @Transactional
    public void enviarComboParaEstrutura(Long comboId, Long estruturaId, List<Long> setoresId,
                                         LocalDate dataInicio, LocalDate dataFim) {

        Combo combo = comboRepository.findById(comboId)
                .orElseThrow(() -> new EntityNotFoundException("Combo não encontrado: " + comboId));

        EstruturaAdm estrutura = estruturaRepository.findById(estruturaId)
                .orElseThrow(() -> new EntityNotFoundException("Estrutura não encontrada: " + estruturaId));

        // Converte LocalDate para LocalDateTime (inicio do dia)
        LocalDateTime dataInicioTime = dataInicio.atStartOfDay();
        LocalDateTime dataFimTime = dataFim.atStartOfDay();

        Competencia competencia = Competencia.builder()
                .ano(dataInicio.getYear())
                .mes(dataInicio.getMonth().name()) // ou formata como quiser
                .dataInicio(dataInicioTime)
                .dataFim(dataFimTime)
                .competenciaStatus(CompetenciaStatus.ABERTO)
                .build();

        competenciaRepository.save(competencia);

        for (Long setorId : setoresId) {
            Setor setor = setorRepository.findById(setorId)
                    .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado: " + setorId));

            ComboDestino destino = ComboDestino.builder()
                    .combo(combo)
                    .estruturaAdm(estrutura)
                    .setor(setor)
                    .competencia(competencia)
                    .ativo(true)
                    .dataEnvio(LocalDateTime.now()) // data/hora atual
                    .build();

            destinoRepository.saveAndFlush(destino);
            System.out.println("Salvo destino -> Estrutura: " + estrutura.getId() + ", Setor: " + setor.getId());
        }
    }
}
