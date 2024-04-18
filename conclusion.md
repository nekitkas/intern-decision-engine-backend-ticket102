DecisionEngine class have multiple responsibilities:
1. Calculating the approved loan amount and period
2. Parsing and validating the ID code
3. Performing calculations related to the credit modifier
4. Verifying inputs

It is better to split these responsibilities into separate classes, classes should implement interfaces

DecisionEngine class is not closed for modification and opened for extension. If there is a need to change logic for calculating the credit modifier, we would need to make changes directly in DecisionEngine class

Also, DecisionEngine directly depends on EstonianPersonalCodeValidator class, instead it should depend on interface rather than concrete implementation.
