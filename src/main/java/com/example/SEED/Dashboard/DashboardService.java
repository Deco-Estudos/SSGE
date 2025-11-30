package com.example.SEED.Dashboard;

import com.example.SEED.Censo.CensoRepository;
import com.example.SEED.Competencia.Competencia;
import com.example.SEED.Competencia.CompetenciaRepository;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired private CensoRepository censoRepository;
    @Autowired private PreenchimentoRepository preenchimentoRepository;
    @Autowired private CompetenciaRepository competenciaRepository;

    public DashboardAnualDTO gerarDashboardAnual(Long estruturaId, int ano) {
        // 1. Busca os meses do ano selecionado
        List<Competencia> competencias = competenciaRepository.findByAnoOrderByDataInicioAsc(ano);
        List<DashboardMensalDTO> historico = new ArrayList<>();

        // Variáveis para somar os totais do ano (KPIs)
        long somaAlunos = 0;
        BigDecimal somaGastoTotal = BigDecimal.ZERO;
        BigDecimal somaGastoFolha = BigDecimal.ZERO;

        // 2. Loop mês a mês
        for (Competencia comp : competencias) {
            // Cálculos individuais do mês (Reutilizando as queries que já fizemos)
            Long alunosMes = censoRepository.somarAlunos(estruturaId, comp.getId());
            if (alunosMes == null) alunosMes = 0L;

            BigDecimal materiaisMes = preenchimentoRepository.somarMateriais(estruturaId, comp.getId());
            if (materiaisMes == null) materiaisMes = BigDecimal.ZERO;

            BigDecimal pessoalMes = preenchimentoRepository.somarPessoal(estruturaId, comp.getId());
            if (pessoalMes == null) pessoalMes = BigDecimal.ZERO;

            BigDecimal totalMes = materiaisMes.add(pessoalMes);

            BigDecimal porAlunoMes = BigDecimal.ZERO;
            if (alunosMes > 0) {
                porAlunoMes = totalMes.divide(new BigDecimal(alunosMes), 2, RoundingMode.HALF_UP);
            }

            // Adiciona ao histórico
            historico.add(new DashboardMensalDTO(
                    comp.getNome(), // ou comp.getMes()
                    alunosMes.intValue(),
                    materiaisMes,
                    pessoalMes,
                    totalMes,
                    porAlunoMes
            ));

            // Acumula nos totais anuais
            somaAlunos += alunosMes;
            somaGastoTotal = somaGastoTotal.add(totalMes);
            somaGastoFolha = somaGastoFolha.add(pessoalMes);
        }

        // 3. Cálculo final dos KPIs
        BigDecimal gastoPorAlunoAnual = BigDecimal.ZERO;
        if (somaAlunos > 0) {

            gastoPorAlunoAnual = somaGastoTotal.divide(new BigDecimal(somaAlunos), 2, RoundingMode.HALF_UP);
        }

        return new DashboardAnualDTO(
                ano,
                somaAlunos,
                somaGastoTotal,
                gastoPorAlunoAnual,
                somaGastoFolha,
                historico
        );
    }
}