<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Ingredient, dao.User,dao.Category, java.util.ArrayList, util.SessionUtils" %>
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
                    
                    <button type="button" class="btn btn-primary mt-3"><a href="formvente">Ajouter une nouvelle vente</a></button>

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
                            <label class="form-label" for="parfum">Filtrer par Parfum</label>
                            <select name="parfum" id="parfum" class="form-control">
                                <option value="">-- Sélectionner un parfum --</option>
                                <option value="nature">Nature</option> <!-- Add option for Nature -->
                                <% 
                                    ArrayList<Ingredient> parfums = (ArrayList<Ingredient>) request.getAttribute("parfum");
                                    if (parfums != null) {
                                        for (Ingredient parfum : parfums) {
                                %>
                                <option value="<%= parfum.getId() %>"><%= parfum.getName() %></option>
                                <% 
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="user">Client</label>
                            <select name="user" id="user" class="form-control">
                                <option value="">-- Sélectionner un utilisateur --</option>
                                <%
                                    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
                                    if (users != null) {
                                        for (User use : users) {
                                %>
                                <option value="<%= use.getId() %>"><%= use.getFirstname() %> <%= use.getLastname() %></option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="date_vente">Sélectionner une date de vente</label>
                            <input type="date" name="date_vente" id="date_vente" class="form-control">
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
                                        <th>id_vente</th>
                                        <th>Date de la vente</th>
                                        <th>Client</th>
                                        <th>Vendeur</th>
                                        <th>Nom du produit</th>
                                        <th>Categorie</th>
                                        <th>is_nature</th>
                                        <th>Unite price</th>
                                        <th>Total amount</th>
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
                                            String vente_date = item[0];          // Sale ID
                                            String client_name = item[1];        // Sale Date
                                            String vendeur_name = item[2];    // User's full name
                                            String recipe = item[3]; // Recipe name (product name)
                                            String categorie_name = item[4]; // Category name
                                            String is_nature = item[5];    // Quantity
                                            String quantity = item[6];  // Unit price
                                            String unit_price = item[7];
                                            String id_vente = item[8];
                                            String total_amount = item[9];
                                %>
                                    <tr>
                                        <td><%= id_vente %></td>
                                        <td><%= vente_date %></td>
                                        <td><%= client_name %></td>              
                                        <td><%= vendeur_name %></td>          
                                        <td><%= recipe %></td>        
                                        <td><%= categorie_name %></td>
                                        <td><%= is_nature %></td>         
                                        <td><%= unit_price %> </td>     
                                        <td><%= total_amount %> Euro</td>
                                    </tr>
                                <% 
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
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
