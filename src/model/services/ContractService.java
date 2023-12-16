package model.services;

import model.entities.Contract;
import model.entities.Installment;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContractService {
    private OnlinePaymentService paymentService;

    public ContractService(OnlinePaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public void processContract(Contract contract, Integer months){
        List<Installment> list = new ArrayList<>();
        for (int i = 1; i<=months;i++){
            LocalDate dueDate = contract.getDate().plus(i, ChronoUnit.MONTHS);
            double totalValue = contract.getTotalValue()/months;
            double payment = totalValue + paymentService.paymentFee(paymentService.interest(totalValue, i)) + paymentService.interest(totalValue, i);
            Installment installment = new Installment(dueDate, payment);

            list.add(installment);
        }
        contract.setInstallments(list);
    }
}
