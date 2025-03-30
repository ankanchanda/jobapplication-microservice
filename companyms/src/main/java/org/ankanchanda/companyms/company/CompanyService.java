package org.ankanchanda.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();

    Company findCompanyById(Long id);

    void createCompany(Company company);

    boolean deleteCompanyById(Long id);

    boolean updateCompany(Long id, Company company);
}
