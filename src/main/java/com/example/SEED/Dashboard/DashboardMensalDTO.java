package com.example.SEED.Dashboard;

import java.math.BigDecimal;

public record DashboardMensalDTO(
        String mes,
        Integer totalAlunos,      // Alunos neste mês
        BigDecimal gastoMateriais,// Gastos neste mês
        BigDecimal gastoPessoal,  // Folha neste mês
        BigDecimal custoTotal,    // Soma
        BigDecimal custoPorAluno  // Eficiência
) {}