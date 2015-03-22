package ru.af.salarycalculator.models; //пакет место расположение класса
import java.util.HashMap; //список использованных сторонних классов
import java.util.Map;
public class Salary{ //заголовок класса
public enum SalaryType{ //енум содержит простой список перечислений
HOUR,
DAY,
MONTH,
YEAR
};
private SalaryFactors salaryFactors; //ссылка на класс SalaryFactors
private Map<SalaryType, Double> salaries = new HashMap<SalaryType, Double>(); //создаём экземпляр класса HashMap
public Salary(SalaryFactors factors){ //конструктор
this.salaryFactors=factors;
}
public SalaryFactors getSalaryFactors() { //метод возвращает SalaryFactors
return salaryFactors;
}
public void setSalaryFactors(SalaryFactors salaryFactors) { //метод устанавливает значение // SalaryFactors
this.salaryFactors = salaryFactors;
}
public Double getSalary(SalaryType salaryType) { //возвращает зп по типу значения из // енума
return salaries.get(salaryType);
}
public void setSalaries(SalaryType salaryType, Double value) { { //устанавливает зп
salaries.put(salaryType, value);
}
}
package ru.af.salarycalculator.models;
import java.math.BigDecimal;
public class SalaryFactors {
//список простых переменных
private float coefExp=1f;//коэф. за стаж
private float exp=0;//стаж
private double interest=100d; //проценты
private double salary= 5554; //зп базовая ставка
private int lengthWorkDay=8; //длина раб дня
private int countWorkDaysMonth=21; //кол раб дней в месяце
public SalaryFactors(){
}
//методы начинающиеся с get возвращают значения
// методы начинающиеся с set устанавливают значения
public float getCoefExp() {
return coefExp;
}
public void setCoefExp(float coefExp) {
this.coefExp = coefExp;
}
public double getInterest() {
return interest;
}
public void setInterest(double interest) {
this.interest = interest;
}
public double getSalary() {
return salary;
}
public void setSalary(double salary) {
this.salary = salary;
}
public float getExp() {
return exp;
}
public void setExp(float exp) {
this.exp = exp;
}
public int getLengthWorkDay() {
return lengthWorkDay;
}
public void setLengthWorkDay(int lengthWorkDay) {
this.lengthWorkDay = lengthWorkDay;
}
public int getCountWorkDaysMonth() {
return countWorkDaysMonth;
}
public void setCountWorkDaysMonth(int countWorkDaysMonth) {
this.countWorkDaysMonth = countWorkDaysMonth;
}
}
package ru.af.salarycalculator.processing;
import java.math.BigDecimal;
import java.math.RoundingMode;
import ru.af.salarycalculator.models.Salary;
import ru.af.salarycalculator.models.SalaryFactors;
public class CalcSalary {
private static CalcSalary calcSalary = new CalcSalary();
private CalcSalary(){
}
public static CalcSalary getCalcSalary(){
return calcSalary;
}
//расчитывает зп за месяц , день, час, год
public void doCalc(Salary salary){
doCalcMonthSalary(salary);
doCalcDaySalary(salary);
doCalcHourSalary(salary);
doCalcYearSalary(salary);
}
private void doCalcMonthSalary(Salary salary){
SalaryFactors factors = salary.getSalaryFactors();
double salaryExp = factors.getSalary()*factors.getExp()*factors.getCoefExp();
double salaryMonth = (factors.getSalary()+salaryExp)*(factors.getInterest()/100f);
salary.setSalaries(Salary.SalaryType.MONTH, customRound(salaryMonth));
}
private void doCalcDaySalary(Salary salary){
SalaryFactors factors = salary.getSalaryFactors();
double salaryDay = salary.getSalary(Salary.SalaryType.MONTH)/ factors.getCountWorkDaysMonth();
salary.setSalaries(Salary.SalaryType.DAY, customRound(salaryDay));
}
private void doCalcHourSalary(Salary salary){
SalaryFactors factors = salary.getSalaryFactors();
double salaryHour = salary.getSalary(Salary.SalaryType.DAY)/factors.getLengthWorkDay();
salary.setSalaries(Salary.SalaryType.HOUR, customRound(salaryHour));
}
private void doCalcYearSalary(Salary salary){
double salaryYear = salary.getSalary(Salary.SalaryType.MONTH)*12f;
salary.setSalaries(Salary.SalaryType.YEAR, customRound(salaryYear));
}
private double customRound(double value){
return new BigDecimal(value).setScale(3, RoundingMode.UP.UP).doubleValue();
}
}
