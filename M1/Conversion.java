
/**
 * M1Assignment1 6.9
 *
 *This program converts feet to meters and meters to Feet
 *then displays them to the user up to 10 of each.
 *
 * @Hailey Sneed
 * @version 1.0
 * @11/01/2025
 * 
 */


public class Conversion {
    public static double footToMeter( double foot){
        return 0.305 * foot;
}
    public static double meterToFoot(double meter) {
        return 3.279 * meter;
    }
    public static void main(String[] args) {
        System.out.println("Feet\tMeters");
        for (int feet = 1; feet <= 10; feet++) {
            System.out.printf("%d\t%.3f%n", feet, footToMeter(feet));
        }
        System.out.println();

        System.out.println("Meters\tFeet");
        for (int meters = 1; meters <= 10; meters++) {
            System.out.printf("%d\t%.3f%n", meters, meterToFoot(meters));
        }
    }
}