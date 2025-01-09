<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.Ingredient, java.util.ArrayList, util.SessionUtils" %>
<%
    boolean connected = SessionUtils.isUserConnected(request);
    String errorMessage = (String) request.getAttribute("errorMessage");
%>

<%@include file="header.jsp" %>

<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
    <div class="layout-container">
        <%@include file="vertical-menu.jsp" %>

        <!-- Layout container -->
        <div class="layout-page">
            <!-- Navbar -->
            <nav
                    class="layout-navbar container-xxl navbar navbar-expand-xl navbar-detached align-items-center bg-navbar-theme"
                    id="layout-navbar"
            >
                <div class="layout-menu-toggle navbar-nav align-items-xl-center me-3 me-xl-0 d-xl-none">
                    <a class="nav-item nav-link px-0 me-xl-4" href="javascript:void(0)">
                        <i class="bx bx-menu bx-sm"></i>
                    </a>
                </div>

                <div class="navbar-nav-right d-flex align-items-center" id="navbar-collapse">

                    <!-- Search modal button trigger -->
                    <button type="button" class="btn btn-icon rounded-pill btn-secondary mx-auto me-2"
                            data-bs-toggle="modal" data-bs-target="#searchModal">
                        <span class="tf-icons bx bx-search"></span>
                    </button>
                    <!-- /Search modal button trigger -->

                    <ul class="navbar-nav flex-row align-items-center">

                        <!-- User -->
                        <%@ include file="user.jsp" %>
                        <!--/ User -->
                    </ul>
                </div>
            </nav>
            <!-- / Navbar -->

            <!-- Content wrapper -->
            <div class="content-wrapper">
                <!-- Content -->
                <div class="container-xxl flex-grow-1 container-p-y">
                    <!-- Search modal -->
                    <div class="modal fade" id="searchModal" tabindex="-1" style="display: none;" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel1">Critères de recherche</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <form method="GET" action="ingredient">
                                        <div class="mb-3">
                                            <label class="form-label" for="search-name">Date et heure de la vente</label>
                                            <input name="searchName" type="date" class="form-control" id="search-name"
                                                   placeholder="Date et heure de la vente" aria-label="Nom" aria-describedby="search-name">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label" for="search-unit">Montant total de la vente</label>
                                            <input name="searchUnit" type="text" class="form-control" id="search-unit"
                                                   placeholder="Montant total de la vente" aria-label="Unité de mesure"
                                                   aria-describedby="search-unit">
                                        </div>
                
                                        <div class="modal-footer p-0">
                                            <button type="reset" class="btn btn-outline-secondary"
                                                    data-bs-dismiss="modal">
                                                Annuler
                                            </button>
                                            <button type="submit" class="btn btn-primary">Valider</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- Search modal -->
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Vente
                    </h4>

                    <!-- Basic Bootstrap Table -->
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
                        <div class="table-responsive text-nowrap" style="overflow-x: visible;">
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
                                <tbody class="table-border-bottom-0">
                                    <% 
                                    ArrayList<String[]> list = (ArrayList<String[]>) request.getAttribute("listes_ventes");
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
                                    <% } %>
                                                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!--/ Basic Bootstrap Table -->
                </div>
                <!-- / Content -->
            </div>
            <!-- / Content wrapper -->
        </div>
        <!-- / Layout container -->
    </div>
</div>
<!-- / Layout wrapper -->

<%@include file="footer.jsp" %>