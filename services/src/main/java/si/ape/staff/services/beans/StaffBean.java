package si.ape.staff.services.beans;

import si.ape.staff.lib.Branch;
import si.ape.staff.lib.Employee;
import si.ape.staff.models.converters.BranchConverter;
import si.ape.staff.models.converters.EmployeeConverter;
import si.ape.staff.models.entities.BranchEntity;
import si.ape.staff.models.entities.EmployeeEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;


@RequestScoped
public class StaffBean {

    private Logger log = Logger.getLogger(StaffBean.class.getName());

    @Inject
    private EntityManager em;

    public Employee getEmployee(Integer employeeId) {
        EmployeeEntity employee = em.find(EmployeeEntity.class, employeeId);

        if (employee == null) {
            return null;
        }

        return EmployeeConverter.toDto(employee);
    }

    public List<Employee> getEmployees() {
        TypedQuery<EmployeeEntity> query = em.createNamedQuery("EmployeeEntity.getAll", EmployeeEntity.class);
        List<EmployeeEntity> employeeEntities = query.getResultList();
        return employeeEntities.stream().map(EmployeeConverter::toDto).collect(java.util.stream.Collectors.toList());
    }
    public List<Employee> getEmployeesAtBranch(Integer branchId) {
        TypedQuery<EmployeeEntity> query = em.createNamedQuery("EmployeeEntity.getAllAtBranch", EmployeeEntity.class);
        query.setParameter("branchId", branchId);
        List<EmployeeEntity> employeeEntities = query.getResultList();
        return employeeEntities.stream().map(EmployeeConverter::toDto).collect(java.util.stream.Collectors.toList());
    }

    public List<Employee> getEmployeesAtBranch(Integer branchId, Integer roleId) {
        TypedQuery<EmployeeEntity> query = em.createNamedQuery("EmployeeEntity.getAllAtBranchWithRole", EmployeeEntity.class);
        query.setParameter("branchId", branchId);
        query.setParameter("roleId", roleId);
        List<EmployeeEntity> employeeEntities = query.getResultList();
        return employeeEntities.stream().map(EmployeeConverter::toDto).collect(java.util.stream.Collectors.toList());
    }

    public List<Branch> getBranchesWithSimilarName(String name) {
        TypedQuery<BranchEntity> query = em.createNamedQuery("BranchEntity.getBranchesWithSimilarName", BranchEntity.class);
        query.setParameter("name", "%" + name + "%");
        List<BranchEntity> branchEntities = query.getResultList();
        return branchEntities.stream().map(BranchConverter::toDto).collect(java.util.stream.Collectors.toList());
    }

    /*public Employee moveEmployee(Integer employeeId, Integer branchId) {
        beginTx();
        EmployeeEntity employee = em.find(EmployeeEntity.class, employeeId);
        try {
            if (employee == null) {
                return null;
            }
            BranchEntity branch = em.find(BranchEntity.class, branchId);
            employee.setBranch(branch);
            em.merge(employee);
        } catch (Exception e) {
            rollbackTx();
            return null;
        }
        commitTx();
        return EmployeeConverter.toDto(employee);
    }*/


    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

}
