package com.turkcell.rentacar.business.constants.messages;

public class BusinessMessages {

    public class RentMessages{
        public static final String RENT_NOT_FOUND = "Rent does not exist with specified id.";
        public static final String INVALID_STARD_DATE = "Start date cannot be later than finish date!";
        public static final String RENTS_LISTED = "Rents' infos listed successfully.";
        public static final String RENT_SAVED = "Rent is created for specified customer.";
        public static final String RENT_FOUND = "Rent found with specified id . ";
        public static final String RENT_UPDATED = "Rent is updated.";
        public static final String RENT_DELETED = "Rent is deleted.";
        public static final String RENTS_FOUND_BY_CAR_ID = "Rents listed for chosen car.";
        public static final String CAR_IS_IN_MAINTENANCE = "Car is in maintenance!";
    }

    public class PaymentMessages{
        public static final String PAYMENT_FAILED = "Payment failed!";
        public static final String PAYMENT_SUCCESSFUL = "Payment has been made successfully!";
        public static final String PAYMENTS_LISTED = "All payments are listed.";
        public static final String PAYMENT_FOUND = "Payment is found by specified id.";
        public static final String PAYMENT_FOUND_BY_CUSOMER = "Payments are listed by specified customer.";
    }

    public class OrderedMessages{
        public static final String ORDERED_ADITOONAL_SERVICES_LISTED = "Ordered Services Listed.";
        public static final String ORDERED_ADITOONAL_SERVICE_ADDED = "Ordered Service is added.";
        public static final String ORDERED_ADITOONAL_SERVICE_LISTED = "Order Service listed.";
        public static final String ORDERED_ADITOONAL_SERVICE_UPDATED = "Ordered Service Updated";
        public static final String ORDERED_ADITOONAL_SERVICE_DELETED = "OrderedService deleted.";
    }

    public class InvoiceMessages{
        public static final String INVOICES_LISTED = "All invoices listed.";
        public static final String INVOICES_ADDED = "Invoice is added successfully.";
        public static final String INVOICES_UPDATED = "Invoice is updated successfully.";
        public static final String INVOICES_DELETED = "Invoice is deleted successfully.";
        public static final String INVOICE_FOUND = "Invoice is found by given id.";
    }

    public class IndividualCustomerMessages{
        public static final String INDIVIDUAL_CUSTOMERS_LISTED = "Individual customers listed.";
        public static final String INDIVIDUAL_CUSTOMER_ADDED = "Individual customer is added.";
        public static final String INDIVIDUAL_CUSTOMER_UPDATED = "Individual customer is updated.";
        public static final String INDIVIDUAL_CUSTOMER_DELETED = "Individual customer is deleted.";
        public static final String INDIVIDUAL_CUSTOMER_FOUND = "Individual customer found by given id.";
    }

    public class CreditCardInformationMessages{
        public static final String CREDIT_CARD_INFORMATIONS_LISTED = "Credit cards infos listed successfully.";
        public static final String CREDIT_CARD_INFORMATION_ADDED = "Credit cards infos are saved successfully.";
        public static final String CREDIT_CARD_INFORMATION_FOUND = "Credit card info is found for specified id.";
        public static final String CREDIT_CARD_INFORMATIONS_FOUND_BY_CUSTOMER_ID = "Credit cards infos are listed for specified customer.";
    }

    public class CorporateCustomerMessages{
        public static final String CORPORATE_CUSTOMERS_LISTED = "Corporate customers listed.";
        public static final String CORPORATE_CUSTOMER_ADDED = "Corporate customer is added.";
        public static final String CORPORATE_CUSTOMER_UPDATED = "Corporate customer is updated.";
        public static final String CORPORATE_CUSTOMER_DELETED = "Corporate customer is deleted.";
        public static final String CORPORATE_CUSTOMER_FOUND = "Corporate customer found by given id.";
    }

    public class ColorMessages{
        public static final String COLORS_LISTED = "Colors listed successfully.";
        public static final String COLOR_ADDED = "Color is added.";
        public static final String COLOR_FOUND = "Color is found with given id.";
        public static final String COLOR_UPDATED = "Color is updated.";
        public static final String COLOR_DELETED = "Color is deleted.";
        public static final String SAME_NAMED_COLORS_ARE_NOT_DESIRED = "Colors can not have same name.";
        public static final String COLOR_DOES_NOT_EXIST = "Color does not exist with id.";
    }

    public class CityMessages{
        public static final String CITIES_LISTED = "Cities listed successfully.";
        public static final String CITY_ADDED = "City is created successfully.";
        public static final String CITY_UPDATED = "City is updated successfully.";
        public static final String CITY_DELETED = "City is deleted successfully.";
        public static final String CITY_FOUND = "City is found successfully.";
        public static final String CITY_DOES_NOT_EXIST = "City does not exist!";
    }

    public class CarMessages{
        public static final String CARS_LISTED = "Cars listed successfuly.";
        public static final String CAR_ADDED = "Car is added.";
        public static final String CAR_UPDATED = "Car is updated.";
        public static final String CAR_DELETED = "Car is deleted.";
        public static final String CAR_FOUND = "Car is found.";
        public static final String CAR_DOES_NOT_EXIST = "Car does not exist !";
    }

    public class CarMaintenanceMessages{
        public static final String CAR_MAINTENANCES_LISTED = "Maintenances listed successfuly.";
        public static final String CAR_MAINTENANCE_ADDED = "Maintenances is added successfuly.";
        public static final String CAR_MAINTENANCE_UPDATED = "Maintenances is updated successfuly.";
        public static final String CAR_MAINTENANCE_DELETED = "Maintenances is deleted successfuly.";
        public static final String CAR_MAINTENANCE_FOUND = "Maintenances is found successfuly.";
        public static final String CAR_MAINTENANCE_DOES_NOT_EXIST = "Car maintenance info does not exist!";
        public static final String CAR_RENTED_ALREADY = "Car has already been rented.";
    }

    public class CarDamageRecordMessages{
        public static final String CAR_DAMAGES_LISTED = "Car's damage records are listed.";
        public static final String CAR_DAMAGE_ADDED = "Car's damage record is added.";
        public static final String CAR_DAMAGE_UPDATED = "Car's damage record is updated.";
        public static final String CAR_DAMAGE_DELETED = "Car's damage record is deleted.";
        public static final String CAR_DAMAGE_FOUND = "Car's damage record is found.";
        public static final String CAR_DAMAGES_FOUND_BY_CAR_ID = "Car's damage record is found by specified car id.";
        public static final String CAR_DAMAGE_DOES_NOT_EXIST = "Car damage info does not exist for id .";
    }

    public class BrandMessages{
        public static final String BRANDS_LISTED = "Brands listed successfully.";
        public static final String BRAND_ADDED = "Brand is added. ";
        public static final String BRAND_FOUND = "Brand is found by id.";
        public static final String BRAND_UPDATED = "Brand is updated.";
        public static final String BRAND_DELETED = "Brand is deleted.";
        public static final String UNIQUE_BRAND_NAME = "There cannot be more than one brand with the same name.";
        public static final String BRAND_DOES_NOT_EXIST = "Brand does not exist!";
    }

    public class AdditionalServiceMessages{
        public static final String ADDITIONAL_SERVICES_LISTED = "Services listed.";
        public static final String ADDITIONAL_SERVICE_ADDED = "Service is saved.";
        public static final String ADDITIONAL_SERVICE_FOUND = "Service found.";
        public static final String ADDITIONAL_SERVICE_UPDATED = "Service is updated.";
        public static final String ADDITIONAL_SERVICE_DELETED = "Service is deleted.";
    }

}
