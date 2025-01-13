<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Ingredient, dao.Category, java.util.ArrayList, util.SessionUtils" %>
<%
    boolean connected = SessionUtils.isUserConnected(request);
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
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Vente</h4>

                    <!-- Category filter form -->
                    <form method="post" action="vente">
                        <div class="mb-3">
                            <label class="form-label" for="categoryId">Filtrer par catégorie</label>
                            <select name="categoryName" id="categoryName" class="form-control">
                                <option value="">-- Sélectionner une catégorie --</option>
                                <% 
                                    ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
                                    if (categories != null) {
                                        for (Category category : categories) {
                                %>
                                <option value="<%= category.getName() %>"><%= category.getName() %></option>
                                <% 
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="isNature">Filtrer par Nature</label>
                            <input type="checkbox" name="isNature" id="isNature" value="true" class="form-check-input">
                            <label class="form-check-label" for="isNature">Nature</label>
                        </div>
                        <button type="submit" class="btn btn-primary">Filtrer</button>
                    </form>

                    <!-- Sales List -->
                    <div class="card">
                        <h5 class="card-header">Liste des ventes</h5>
                        <div class="card-body">
                            <% if (connected) { %>
                                <div class="mb-3">
                                    <a href="form-ingredient" type="button" class="btn btn-success">Ajouter</a>
                                </div>
                            <% } %>
                            <% if(errorMessage != null) { %>
                                <div class="alert alert-danger alert-dismissible mb-0" role="alert">
                                    <%= errorMessage %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                            <% } %>
                        </div>
                        <div class="table-responsive text-nowrap">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>id vente</th>
                                        <th>Date de la vente</th>
                                        <th>Nom de l'user</th>
                                        <th>Nom du produit</th>
                                        <th>Quantité</th>
                                        <th>Unite price</th>
                                        <th>Total</th>
                                        <% if (connected) { %>
                                            <th>Actions</th>
                                        <% } %>
                                    </tr>
                                </thead>
                                <tbody>
                                    <% 
                                        ArrayList<String[]> list = (ArrayList<String[]>) request.getAttribute("filteredSales");
                                        if (list == null) {
                                            list = (ArrayList<String[]>) request.getAttribute("liste_ventes");
                                        }
                                        if (list != null) {
                                            for (String[] item : list) { 
                                                String id = item[0];          
                                                String daty = item[1];        
                                                String nom_user = item[2];    
                                                String nom_produit = item[3]; 
                                                String quantite = item[4];    
                                                String unit_price = item[5];  
                                                String total = item[6];      
                                    %>
                                    <tr>
                                        <td><strong><%= id %></strong></td>
                                        <td><%= daty %></td>              
                                        <td><%= nom_user %></td>          
                                        <td><%= nom_produit %></td>        
                                        <td><%= quantite %></td>         
                                        <td><%= unit_price %> Ar</td>     
                                        <td><%= total %> Ar</td>         
                                        <% if (connected) { %>
                                        <td>
                                            <div class="dropdown">
                                                <button type="button" class="btn p-0 dropdown-toggle hide-arrow" data-bs-toggle="dropdown">
                                                    <i class="bx bx-dots-vertical-rounded"></i>
                                                </button>
                                                <div class="dropdown-menu">
                                                    <a class="dropdown-item" href="form-ingredient?action=update&id=<%= id %>">
                                                        <i class="bx bx-edit-alt me-1"></i> Modifier
                                                    </a>
                                                    <a class="dropdown-item" href="ingredient?action=delete&id=<%= id %>">
                                                        <i class="bx bx-trash me-1"></i> Supprimer
                                                    </a>
                                                </div>
                                            </div>
                                        </td>
                                        <% } %>
                                    </tr>
                                    <% 
                                            }
                                        }
                                    %>
                                </tbody>
                            </table>
                            <a href="formvente">Ajouter Vente</a>
                        </div>
                    </div>
                    <!--/ Sales List -->
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
