package com.example.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "stars", schema = "public")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Positive
    @Column(nullable = false)
    private double solarRadius;

    @Positive
    @Column(nullable = false)
    private double solarMass;

    @Column(nullable = false)
    private Instant dateCreated = Instant.now();

    private static final int SUN_RADIUS = 696340;
    private static final double GRAVITATIONAL_CONSTANT = 6.67430e-11; // m³·kg⁻¹·s⁻²
    private static final double SUN_LUMINOSITY = 3.828e26; // W (watts)
    private static final double SUN_MASS = 1.9885e30; // kg
    private static final double STEFAN_BOLTZMANN_CONSTANT = 5.670374419e-8;
    private static final double WEIN_DISPLACEMENT_CONSTANT = 2.897771955e-3;

    public Star() {}

    public Star(String name, double solarRadius, double solarMass) {
        this.name = name;
        this.solarRadius = solarRadius;
        this.solarMass = solarMass;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSolarRadius() {
        return solarRadius;
    }

    @Transient
    public double getRadius() {
        return solarRadius * SUN_RADIUS * 1000; // m
    }

    @Transient
    public double getSurfaceArea() {
        double radius = getRadius();
        return 4 * Math.PI * radius * radius; // m²
    }

    @Transient
    public double getVolume() {
        double radius = getRadius();
        return (4.0 / 3.0) * Math.PI * radius * radius * radius; // m³
    }

    @Transient
    public double getDiameter() {
        return 2 * getRadius(); // m
    }

    @Transient
    public double getCircumference() {
        return 2 * Math.PI * getRadius(); // m
    }

    public double getSolarMass() {
        return solarMass; // M(.)
    }

    @Transient
    public double getMassKg() {
        return solarMass * SUN_MASS; // kg
    }

    @Transient
    public double getMeanDensity() {
        return getMassKg() / getVolume(); // kg/m³
    }

    @Transient
    public double getSurfaceGravity() {
        double radius = getRadius();
        return GRAVITATIONAL_CONSTANT * getMassKg() / (radius * radius); // m/s²
    }

    // Mass-Luminosity relation
    // This gives us a value relative to our own sun - i.e. 2.0 is twice as bright as the sun
    @Transient
    public double getRelativeLuminosity() {
        return Math.pow((getMassKg() / SUN_MASS), 3.5);
    }

    // This gives our absolute watts
    @Transient
    public double getAbsoluteLuminosity() {
        return getRelativeLuminosity() * SUN_LUMINOSITY; // W
    }

    @Transient
    public double getSurfaceTemperature() {
        return Math.pow(getAbsoluteLuminosity() / (4 * Math.PI * Math.pow(getRadius(), 2) * STEFAN_BOLTZMANN_CONSTANT), 0.25); // K
    }

    @Transient
    public double getPeakWaveLength() {
        return (WEIN_DISPLACEMENT_CONSTANT / getSurfaceTemperature()) * 1e9; // nm
    }

    @Transient
    public double getHabitableZoneInner() {
        return 0.99 * Math.sqrt(getRelativeLuminosity()); // AU
    }

    @Transient
    public double getHabitableZoneOuter() {
        return 1.7 * Math.sqrt(getRelativeLuminosity()); // AU
    }

    @Transient
    public double getHabitableZoneMean() {
        return (getHabitableZoneInner() + getHabitableZoneOuter()) / 2; // AU
    }

    @Transient
    public double getOrbitalPeriodInner() {
        return Math.sqrt(Math.pow(getHabitableZoneInner(), 3) / (getMassKg() / SUN_MASS)); // Earth years
    }

    @Transient
    public double getOrbitalPeriodOuter() {
        return Math.sqrt(Math.pow(getHabitableZoneOuter(), 3) / (getMassKg() / SUN_MASS)); // Earth years
    }

    @Transient
    public double getOrbitalPeriodMean() {
        return Math.sqrt(Math.pow(getHabitableZoneMean(), 3) / (getMassKg() / SUN_MASS)); // Earth years
    }

    // See spectral classification (OBAFGKM)
    @Transient
    public String getSpectralType() {
        double temp = getSurfaceTemperature();

        if (temp >= 30000) return "O (Blue)";
        if (temp >= 10000) return "B (Blue-White)";
        if (temp >= 7500) return "A (White)";
        if (temp >= 6000) return "F (Yellow-White)";
        if (temp >= 5200) return "G (Yellow)";
        if (temp >= 3700) return "K (Orange)";
        return "M (Red)";
    }

    @Transient
    public String getStarType() {
        final double mass = getSolarMass();
        final double radiusKm = getRadius() / 1000.0;
        final double temp = getSurfaceTemperature();

        // Neutron Star
        if (mass >= 1.1 && mass < 2.3 && radiusKm < 30) {
            return "Neutron Star";
        }

        // White dwarf
        if (mass <= 1.4 && radiusKm >= 5000 && radiusKm <= 20000) {
            return "White Dwarf";
        }

        // Black hole check
        double c = 3e8; // m/s
        double schwarzschildRadiusM = (2 * GRAVITATIONAL_CONSTANT * getMassKg()) / (c * c);
        double schwarzschildRadiusKm = schwarzschildRadiusM / 1000.0;
        if (radiusKm <= schwarzschildRadiusKm) {
            return "Black Hole";
        }

        // Red giant
        if (mass <= 10 && radiusKm > 1000000) { // ~1,000,000 km
            return "Red Giant";
        }

        return "Main Sequence Star";
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSolarRadius(double solarRadius) {
        this.solarRadius = solarRadius;
    }

    public void setSolarMass(double solarMass) {
        this.solarMass = solarMass;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated.toInstant();
    }
}