public class OS {
    private static String[] memory = {"C050005C", "4B060000", "4B010000", "4B000000", "4F0A005C", "4F0D00DC", "4C0A0004", "C0BA0000", "42BD0000", "4C0D0004", "4C060001", "10658000", "56810018", "4B060000", "4F0900DC", "43970000", "05070000", "4C060001", "4C090004", "10658000", "5681003C", "C10000AC", "92000000"};
    private static String[] data;
    public static void main(String[] args) {
        CPU cpu = new CPU(memory);
        cpu.run();
    }
}