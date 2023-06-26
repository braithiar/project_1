package com.revature.controllers;

import com.revature.dao.ExpenseTypeDAO;
import com.revature.dao.StatusDAO;
import com.revature.models.Reimbursement;
import com.revature.security.TokenGenerator;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class ReimbursementController {
  private final ReimbursementService reimbService;
  private final UserService userService;
  private final StatusDAO statusDAO;
  private final ExpenseTypeDAO expenseTypeDAO;
  private final TokenGenerator tokenGenerator;

  @Autowired
  public ReimbursementController(ReimbursementService reimbService,
                                 UserService userService, StatusDAO statusDAO,
                                 ExpenseTypeDAO expenseTypeDAO, TokenGenerator tokenGenerator) {
    this.reimbService = reimbService;
    this.userService = userService;
    this.statusDAO = statusDAO;
    this.expenseTypeDAO = expenseTypeDAO;
    this.tokenGenerator = tokenGenerator;
  }

  @GetMapping("{uid}/reimbursements")
  public List<Reimbursement> getAllUserReimbursementsHandler(
    @PathVariable("uid") int uid) {
    return reimbService.getAllUserReimbursements(
      userService.getUserById(uid)
    );
  }

  @GetMapping("{uid}/reimbursements/pending")
  public List<Reimbursement> getUserPendingReimbursementsHandler(
    @PathVariable("uid") int uid) {
    return reimbService.getUserReimbursementsByStatus(
      userService.getUserById(uid),
      statusDAO.findByName("Pending")
    );
  }

  @GetMapping("{uid}/reimbursements/approved")
  public List<Reimbursement> getUserApprovedReimbursementsHandler(
    @PathVariable("uid") int uid) {
    return reimbService.getUserReimbursementsByStatus(
      userService.getUserById(uid),
      statusDAO.findByName("Approved")
    );
  }

  @GetMapping("{uid}/reimbursements/denied")
  public List<Reimbursement> getUserDeniedReimbursementsHandler(
    @PathVariable("uid") int uid) {
    return reimbService.getUserReimbursementsByStatus(
      userService.getUserById(uid),
      statusDAO.findByName("Denied")
    );
  }

  @GetMapping("reimbursements")
  public List<Reimbursement> getAllReimbursementsHandler() {
    return reimbService.getAllReimbursements();
  }

  @GetMapping("reimbursements/pending")
  public List<Reimbursement> getAllPendingReimbursementsHandler() {
    return reimbService.getAllReimbursementsByStatus(
      statusDAO.findByName("Pending")
    );
  }

  @GetMapping("reimbursements/approved")
  public List<Reimbursement> getAllApprovedReimbursementsHandler() {
    return reimbService.getAllReimbursementsByStatus(
      statusDAO.findByName("Approved")
    );
  }

  @GetMapping("reimbursements/denied")
  public List<Reimbursement> getAllDeniedReimbursementsHandler() {
    return reimbService.getAllReimbursementsByStatus(
      statusDAO.findByName("Denied")
    );
  }

  @GetMapping("{uid}/reimbursements/travel")
  public List<Reimbursement> getAllTravelReimbursementsHandler() {
    return reimbService.getAllReimbursementsByType(
      expenseTypeDAO.findByType("Travel")
    );
  }

  @GetMapping("{uid}/reimbursements/lodging")
  public List<Reimbursement> getAllLodgingReimbursementsHandler() {
    return reimbService.getAllReimbursementsByType(
      expenseTypeDAO.findByType("Lodging")
    );
  }

  @GetMapping("{uid}/reimbursements/food")
  public List<Reimbursement> getAllFoodReimbursementsHandler() {
    return reimbService.getAllReimbursementsByType(
      expenseTypeDAO.findByType("Food")
    );
  }

  @GetMapping("{uid}/reimbursements/other")
  public List<Reimbursement> getAllOtherReimbursementsHandler() {
    return reimbService.getAllReimbursementsByType(
      expenseTypeDAO.findByType("Other")
    );
  }

  @PostMapping("{uid}/reimbursements")
  public Reimbursement createReimbursementHandler(@PathVariable("uid") int uid,
                                                  @RequestBody Reimbursement r) {
    return reimbService.createReimbursement(uid, r);
  }


}
