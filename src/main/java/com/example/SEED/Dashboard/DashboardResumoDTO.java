package com.example.SEED.Dashboard;

import java.math.BigDecimal;

public record DashboardResumoDTO(
        String nomeEstrutura,       // Nome da Escola (ou "Geral")
        Long totalAlunos,           // Do Censo
        BigDecimal gastoMateriais,  // Dos Kits
        BigDecimal gastoPessoal,    // Do RH
        BigDecimal custoTotal,      // Soma dos dois
        BigDecimal custoPorAluno    // Métrica de eficiência
) {}