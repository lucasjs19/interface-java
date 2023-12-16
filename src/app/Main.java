package app;

import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Entre os dados do contrato:");
        System.out.print("Numero: ");
        int numero = sc.nextInt();
        sc.nextLine();
        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate data = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Valor do contrato: ");
        Double valor = sc.nextDouble();
        System.out.print("Entre com o numero de parcelas: ");
        int parcelas = sc.nextInt();

        Contract contract = new Contract(numero,data,valor);

        ContractService contractService = new ContractService(new PaypalService());
        contractService.processContract(contract,parcelas);

        System.out.println("PARCELAS:");

        for (Installment installment: contract.getInstallments()) {
            System.out.println(installment.getDueDate().format(fmt)
                    + " - "
                    + String.format("%.2f",installment.getAmount()));
        }
    }
}