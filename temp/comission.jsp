<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.CommissionViewDAO, dao.CommissionSexe, dao.User, java.util.List, util.SessionUtils" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<%@ include file="header.jsp" %> <!-- Header section -->

<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
    <div class="layout-container">

        <%@ include file="vertical-menu.jsp" %> <!-- Vertical Menu -->

        <!-- Layout container -->
        <div class="layout-page">
            <!-- Navbar -->
            <nav class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme" id="layout-navbar">
                <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                    <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                        <i class="bx bx-menu bx-sm"></i>
                    </a>
                </div>

                <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">
                    <button type="button" class="btn btn-icon rounded-pill btn-secondary mx-auto me-2" data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="tf-icons bx bx-search"></span>
                    </button>
                    <ul class="navbar-nav flex-row align-items-center">
                        <%@ include file="user.jsp" %> <!-- User section -->
                    </ul>
                </div>
            </nav>
            <!-- / Navbar -->

            <!-- Content wrapper -->
            <div class="content-wrapper">
                <!-- Content -->
                <div class="container-xxl flex-grow-1 container-p-y">
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Commissions</h4>

                    <div class="table-responsive text-nowrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>Sexe</th>
                                        <th>Total Commission</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <% 
                                    List<CommissionSexe> commissionsSexe = (List<CommissionSexe>) request.getAttribute("commissionsSexe");
                                    if (commissionsSexe != null && !commissionsSexe.isEmpty()) {
                                        for (CommissionSexe commission : commissionsSexe) {
                                            String sexe = commission.getSexe();
                                            double totalCommission = commission.getTotalCommission();
                                %>
                                            <tr>
                                                <td><%= sexe %></td>
                                                <td><%= totalCommission %> Euro</td>
                                            </tr>
                                <% 
                                        }
                                    } else {
                                %>
                                        <tr>
                                            <td colspan="2" class="text-center">Aucune commission par sexe disponible pour l'instant.</td>
                                        </tr>
                                <% 
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                        
                    <!-- Commission List -->
                    <div class="card">

                        

                        <div class="card-body">
                            <form method="POST" action="commissions" class="row g-3">
                                <div class="col-md-4">
                                    <label for="startDate" class="form-label">Date de début</label>
                                    <input type="date" id="startDate" name="startDate" class="form-control">
                                </div>
                                <div class="col-md-4">
                                    <label for="endDate" class="form-label">Date de fin</label>
                                    <input type="date" id="endDate" name="endDate" class="form-control">
                                </div>
                                <div class="col-md-4">
                                    <label for="vendeur" class="form-label">Vendeur</label>
                                    <select id="vendeur" name="vendeur" class="form-select">
                                        <option value="">Tous les vendeurs</option>
                                        <% List<User> vendeurs = (List<User>) request.getAttribute("vendeurs");
                                        if (vendeurs != null) {
                                            for (User vendeur : vendeurs) { %>
                                                <option value="<%= vendeur.getId() %>"><%= vendeur.getFirstname() %></option>
                                            <% }
                                        } %>
                                    </select>
                                </div>
                                <div class="col-md-12">
                                    <button type="submit" class="btn btn-primary">Filtrer</button>
                                </div>
                            </form>
                        </div>
                        <div class="table-responsive text-nowrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>id commission</th>
                                        <th>id vente</th>
                                        <th>Date de vente</th>
                                        <th>Montant total</th>
                                        <th>Pourcentage de commission</th>
                                        <th>Montant commission</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <% 
                                    List<CommissionViewDAO> commissions = (List<CommissionViewDAO>) request.getAttribute("commissions");
                                    if (commissions != null && !commissions.isEmpty()) {
                                        for (CommissionViewDAO commission : commissions) {
                                            int idCommission = commission.getIdCommission();
                                            int idVendeur = commission.getIdVendeur();
                                            int idVente = commission.getIdVente();
                                            String dateVente = commission.getVenteDate().toString(); 
                                            String montantTotal = commission.getTotalAmount().toString();
                                            String pourcentageCommission = commission.getPourcentageCommission().toString();
                                            String montantCommission = commission.getMontantCommission().toString();
                                %>
                                    <tr>
                                        <td><strong><%= idCommission %></strong></td>
                                        <td><%= idVente %></td> 
                                        <td><%= dateVente %></td>              
                                        <td><%= montantTotal %> Euro</td>
                                        <td><%= pourcentageCommission %> %</td>
                                        <td><%= montantCommission %> Euro</td>
                                    </tr>
                                <% 
                                        }
                                    } else {
                                %>
                                    <tr>
                                        <td colspan="7" class="text-center">Aucune commission disponible pour l'instant. Connectez-vous pour voir les commissions.</td>
                                    </tr>
                                <% 
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- / Commission List -->
                </div>
                <!-- / Content -->
            </div>
            <!-- / Content wrapper -->
        </div>
        <!-- / Layout container -->
    </div>
</div>
<!-- / Layout wrapper -->

<%@ include file="footer.jsp" %> <!-- Footer section -->

<!-- Add Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
