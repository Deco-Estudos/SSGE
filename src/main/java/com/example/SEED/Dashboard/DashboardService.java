package com.example.SEED.Dashboard;

import com.example.SEED.Censo.CensoRepository;
import com.example.SEED.EstruturaAdm.EstruturaAdm;
import com.example.SEED.EstruturaAdm.EstruturaAdmRepository;
import com.example.SEED.Preenchimento.PreenchimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DashboardService {

    @Autowired
    private CensoRepository censoRepository;

    @Autowired
    private PreenchimentoRepository preenchimentoRepository;

    @Autowired
    private EstruturaAdmRepository estruturaRepository;

    public DashboardResumoDTO calcularResumo(Long estruturaId, Long competenciaId) {

        // 1. Busca nome da estrutura para exibição (opcional, mas bom para debug)
        String nome = "Visão Geral";
        if (estruturaId != null) {
            EstruturaAdm est = estruturaRepository.findById(estruturaId).orElse(null);
            if (est != null) nome = est.getName();
        }

        // 2. Busca Total de Alunos (Censo)
        Long totalAlunos = censoRepository.somarAlunos(estruturaId, competenciaId);
        if (totalAlunos == null) totalAlunos = 0L;

        // 3. Busca Gasto com Pessoal (RH)
        // Usa a query somarPessoal que criamos no PreenchimentoRepository
        BigDecimal gastoPessoal = preenchimentoRepository.somarPessoal(estruturaId, competenciaId);
        if (gastoPessoal == null) gastoPessoal = BigDecimal.ZERO;

        // 4. Busca Gasto com Materiais (Kits)
        // Usa a query somarMateriais que criamos no PreenchimentoRepository
        BigDecimal gastoMateriais = preenchimentoRepository.somarMateriais(estruturaId, competenciaId);
        if (gastoMateriais == null) gastoMateriais = BigDecimal.ZERO;

        // 5. Cálculos Matemáticos
        BigDecimal custoTotal = gastoPessoal.add(gastoMateriais);

        BigDecimal custoPorAluno = BigDecimal.ZERO;
        if (totalAlunos > 0) {
            custoPorAluno = custoTotal.divide(new BigDecimal(totalAlunos), 2, RoundingMode.HALF_UP);
        }

        // Retorna o DTO preenchido
        return new DashboardResumoDTO(
                nome,
                totalAlunos,
                gastoMateriais,
                gastoPessoal,
                custoTotal,
                custoPorAluno
        );
    }
}