import java.util.Scanner;

public class CPU {
    Scanner sc =new Scanner(System.in);
    //Reg-0 is the accumulator and Reg-1 is the zero register
    //All other registers are general purpose

    private String[] RAM = new String[8];
    private String[] registers = new String[16];
    private int PC;
    private boolean running = false;

    public CPU(String[] memory){
        //copy memory into RAM
        System.arraycopy(memory,0,RAM,0,8);
        PC = 0;
    }
    public void run(){
        running = true;
        while(running){
            if(PC > registers.length){
                PC = registers.length - 1;
            }
            String opcd = registers[PC].substring(0,1);
           switch (opcd){
               case "C0":
                   RD();
               break;
               case "C1":
                   WR();
               break;
               case "42":
                   ST();
               break;
               case "43":
                   LW();
               break;
               case "04":
                   MOV();
               break;
               case "05":
                   ADD();
               break;
               case "06":
                   SUB();
               break;
               case "07":
                   MUL();
               break;
               case "08":
                   DIV();
               break;
               case "09":
                   AND();
               break;
               case "4A":
                   OR();
               break;
               case "4B":
                   MOVI();
               break;
               case "4C":
                   ADDI();
               break;
               case "4D":
                   MULI();
               break;
               case "4E":
                   DIVI();
               break;
               case "4F":
                   LDI();
               break;
               case "10":
                   SLT();
               break;
               case "11":
                    SLTI();
               break;
               case "92":
                   HLT();
               break;
               case "13":
                   NOP();
               break;
               case "14":
                   JMP();
               break;
               case "55":
                   BEQ();
               break;
               case "56":
                   BNE();
               break;
               case "57":
                   BEZ();
               break;
               case "58":
                   BNZ();
               break;
               case "59":
                   BGZ();
               break;
               case "1A":
                   BLZ();
               break;
               default:
                   System.out.println("Error incorrect opcode detected at register " + PC);
           }
           increment();
        }

        //Move to next half of memory
        System.arraycopy(registers,0,RAM,0,16);
        System.arraycopy(RAM, 17,registers,0,16);
    }
    public void increment(){
        PC++;
    }
    //reads the content at register two into instruction register
    public void WR(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String address = registers[PC].substring(4);
        int hexAddress = Integer.parseInt(address, 16);

        if (hex2 == 0000){
            System.out.println("Writing data in register " + reg1 + " into register " + reg2 + "...");
            registers[hex2] = Integer.toHexString(hex1).toUpperCase();
        }else{
            System.out.println("Writing data in register " + reg1 + " into register address...");
            registers[PC]=registers[PC].replace(Integer.toHexString(hexAddress), "000000000000000" + Integer.toHexString(hex1));
            registers[PC] = registers[PC].toUpperCase();
        }
    }
    public void RD(){
       //Get the base 10 equivalent of register 1
       String reg1;
       reg1 = "" + registers[PC].charAt(2);
       int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String address = registers[PC].substring(4);
        int hexAddress = Integer.parseInt(address, 16);

       //read the content of Reg 2 into Reg 1.
       System.out.println("Reading address into register " + hex1+ "...");
       registers[hex1] = "0000"+ address;
    }
    public void ST(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String address = registers[PC].substring(4);
        int hexAddress = Integer.parseInt(address, 16);

        if (hex2 == 0000){
            //store reg-1 content into reg-2
            System.out.println("Writing data in register " + reg1 + " into register " + reg2 + "...");
            registers[hex2] = Integer.toHexString(hex1).toUpperCase();
        }else{
            //store the content of register 1 into the address
            System.out.println("Writing data in register " + reg1 + " into  address...");
            registers[PC]=registers[PC].replace(Integer.toHexString(hexAddress), "000000000000000" + Integer.toHexString(hex1));
            registers[PC] = registers[PC].toUpperCase();
        }
    }
    public void LW() {
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String address = registers[PC].substring(4);
        int hexAddress = Integer.parseInt(address, 16);

        if (hex1 == 0000) {
            //store reg-1 content into reg-2
            System.out.println("Loading data in register " + reg2 + " into register " + reg1);
            registers[hex1] = Integer.toHexString(hex2).toUpperCase();
        } else {
            //store the content of register 1 into the address
            System.out.println("Loading address data into register " + reg1+ "...");
            registers[hex2] = "0000" + address;
        }
    }
    public void MOV(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //sets the first register equal to the second register's content and empties the second register
        System.out.println("Transferring content from register " + reg1 + " into " + reg2 + "...");
        registers[hex1] = registers[hex2];
        registers[hex2] = "00000000";
    }
    //add contents of reg
    public void ADD(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        System.out.println("Calculating addition operation...");
        //add reg-2 and reg-3 and store in reg-1
        int result = hex2 + hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void SUB(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        System.out.println("Calculating subtraction operation...");
        //subtracting reg-2 and reg-3 and store in reg-1
        int result = hex2 - hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void MUL(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        System.out.println("Calculating multiplication operation...");
        //multiplying reg-2 and reg-3 and store in reg-1
        int result = hex2 * hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void DIV(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        System.out.println("Calculating divide operation...");
        //dividing reg-2 and reg-3 and store in reg-1
        int result = hex2 / hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void AND(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);


        System.out.println("Calculating AND operation...");
        //And reg-2 and reg-3 and store in reg-1
        int result = hex2 & hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void OR(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        System.out.println("Calculating OR operation...");
        //or reg-2 and reg-3 and store in reg-1
        int result = hex2 | hex3;
        //convert back into hexadecimal beforehand
        registers[hex1] = Integer.toHexString(result);
    }
    public void MOVI(){
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address and move it into the register
        String add = registers[PC].substring(4);
        int hexAdd = Integer.parseInt(add, 16);

        System.out.println("Moving data into register...");
        registers[hex2] = Integer.toHexString(hexAdd).toUpperCase();
    }

    public void ADDI(){
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String add = registers[PC].substring(4);
        int hexAdd = Integer.parseInt(add, 16);

        //Adds the content of the register to the given value and store back into the register as hex
        System.out.println("Adding " + hexAdd + " to " + hex2 + " and placing result into register " + registers[PC] + "...");
        int result = hexAdd + Integer.parseInt(registers[hex2]);

        registers[hex2] = Integer.toHexString(result).toUpperCase();
    }
    public void MULI(){
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String add = registers[PC].substring(4);
        int hexMul = Integer.parseInt(add, 16);

        //Multiply the content of the register to the given value and store back into the register as hex
        System.out.println("Adding " + hexMul + " to " + hex2 + " and placing result into register " + PC + "...");
        int result = hexMul * Integer.parseInt(registers[hex2]);
        registers[hex2] = Integer.toHexString(result).toUpperCase();
    }
    public void DIVI(){
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address
        String add = registers[PC].substring(4);
        int hexDiv = Integer.parseInt(add, 16);

        //Divide the content of the register to the given value and store back into the register as hex
        System.out.println("Adding " + hexDiv + " to " + hex2 + " and placing result into register " + PC);
        int result = hexDiv / Integer.parseInt(registers[hex2]);
        registers[hex2] = Integer.toHexString(result).toUpperCase();
    }
    public void LDI(){
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address and move it into the register
        String add = registers[PC].substring(4);
        int hexAdd = Integer.parseInt(add, 16);

        System.out.println("Loading data into register...");
        registers[hex2] = Integer.toHexString(hexAdd).toUpperCase();
    }
    public void SLT(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the base 10 equivalent of reg 3
        String reg3;
        reg3 = "" + registers[PC].charAt(4);
        int hex3 = Integer.parseInt(reg3, 16);

        //set reg-1 to 1 or 0 depending on reg-2 and reg-3
        if (hex2 < hex3){
            System.out.println("Setting register " + hex1 + " equal to 1");
            registers[hex1] = "00000001";
        }else{
            System.out.println("Setting register " + hex1 + " equal to 0");
            registers[hex1] = "00000000";
        }
    }
    public void SLTI(){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + registers[PC].charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + registers[PC].charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //Get the 16-bit address and move it into the register
        String add = registers[PC].substring(4);
        int hexAdd = Integer.parseInt(add, 16);

        if (hex2 < hexAdd){
            System.out.println("Setting register " + hex1 + " equal to 1...");
            registers[hex1] = "00000001";
        }else{
            System.out.println("Setting register " + hex1 + " equal to 0...");
            registers[hex1] = "00000000";
        }
    }
    public void HLT(){
        running = false;
    }
    public void NOP(){
    }
    public void JMP(){
        //asks for the target address and convert to hex
        System.out.print("Enter the target value to jump to: ");
        int target = sc.nextInt();
        String hexTarget = Integer.toHexString(target).toUpperCase();
        int count = 0;

        //iterate through the registers to find the address
        for(String c : registers){
            String test = c.substring(4);
            if (hexTarget.equals(test)){
                PC = count;
            }else {
                count++;
            }
        }
        System.out.println("Jumping to point with the same address...");
        //preemptively lowers PC to return to the address after incrementing
        PC--;
    }
    public void BEQ(){
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for(String c : registers){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + c.charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //compare the base 10 integers
        String test = c.substring(4);
        if (hex1 == hex2){
            PC = count;
        }else {
            count++;
        }
    }
        System.out.println("Branching to point where registers are equal...");
        //preemptively lowers PC to return to the address after incrementing
        PC--;
    }
    public void BNE(){
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for(String c : registers){
        //Get the base 10 equivalent of register 1
        String reg1;
        reg1 = "" + c.charAt(2);
        int hex1 = Integer.parseInt(reg1, 16);

        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //compare the base 10 integers
        String test = c.substring(4);
        if (hex1 != hex2){
            PC = count;
        }else {
            count++;
        }
    }
        System.out.println("Branching to point where registers do not equal...");
        //preemptively lowers PC to return to the address after incrementing
        PC--;
    }
    public void BEZ() {
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for (String c : registers) {
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //find where the base 10 int with 0
        if (hex2 == 0) {
            PC = count;
        } else {
            count++;
        }
    }
        System.out.println("Branching to point where register is equal to 0...");
    //preemptively lowers PC to return to the address after incrementing
    PC--;
    }
    public void BNZ(){
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for (String c : registers) {
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //compare the base 10 int does not equal 0
        if (hex2 != 0) {
            PC = count;
        } else {
            count++;
        }
    }
    System.out.println("Branching to point where register does not equal 0...");
    //preemptively lowers PC to return to the address after incrementing
    PC--;
    }
    public void BGZ(){
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for (String c : registers) {
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //find where 0 is over 0
        if (hex2 > 0) {
            PC = count;
        } else {
            count++;
        }
    }
    System.out.println("Branching to point where register is over 0...");
    //preemptively lowers PC to return to the address after incrementing
    PC--;
    }
    public void BLZ(){
        int count = 0;
        //iterate through the registers to find the instruction where reg-1 equals reg-2
        for (String c : registers) {
        //Get the base 10 equivalent of register 2
        String reg2;
        reg2 = "" + c.charAt(3);
        int hex2 = Integer.parseInt(reg2, 16);

        //find where register is less than 0
        if (hex2 < 0) {
            PC = count;
        } else {
            count++;
        }
    }
    System.out.println("Branching to point where register is less than 0...");
    //preemptively lowers PC to return to the address after incrementing
    PC--;
    }
}
