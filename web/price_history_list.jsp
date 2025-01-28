<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dao.PriceHistory, java.util.ArrayList, dao.Recipe, java.util.Date, util.SessionUtils" %>
<% boolean connected = SessionUtils.isUserConnected(request); %>

<%@ include file="header.jsp" %>

<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
    <div class="layout-container">
        <%@ include file="vertical-menu.jsp" %>

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
                    <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Gotta taste /</span> Price History</h4>

                    <!-- Search Form -->
                    <form action="history" method="post">
                        <div class="mb-3">
                            <label for="recipe" class="form-label">Recipe:</label>
                            <select name="recipeId" id="recipe" class="form-select">
                                <option value="0">--Select Recipe--</option>
                                <% 
                                    ArrayList<Recipe> recipes = (ArrayList<Recipe>) request.getAttribute("recipes");
                                    for (Recipe recipe : recipes) {
                                %>
                                    <option value="<%= recipe.getId() %>"><%= recipe.getTitle() %></option>
                                <% } %>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label for="minDate" class="form-label">Min Date:</label>
                            <input type="date" name="minDate" id="minDate" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="maxDate" class="form-label">Max Date:</label>
                            <input type="date" name="maxDate" id="maxDate" class="form-control">
                        </div>

                        <button type="submit" class="btn btn-primary">Search</button>
                    </form>

                    <!-- Basic Bootstrap Table -->
                    <div class="card mt-4">
                        <h5 class="card-header">Liste des Historique des Prix</h5>
                        <% if (connected) { %>
                        <div class="card-body"><a href="form-price-history" type="button" class="btn btn-success">Ajouter un Historique de Prix</a></div>
                        <% } %>
                        <div class="table-responsive text-nowrap" style="overflow: auto visible">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nom du Recipe</th>
                                    <th>Prix</th>
                                    <th>Date de debut</th>
                                    <th>Date fin</th>
                                    <th>Actions</th>
                                </tr>
                                </thead>
                                <tbody class="table-border-bottom-0">
                                <% 
                                    ArrayList<PriceHistory> priceHistories = (ArrayList<PriceHistory>) request.getAttribute("priceHistories");
                                    for (PriceHistory priceHistory : priceHistories) {
                                %>
                                <tr>
                                    <td></td>
                                    <td><strong><%= priceHistory.getRecipe().getTitle() %></strong></td>
                                    <td><%= priceHistory.getPrice() %> Ar</td>
                                    <td><%= priceHistory.getStartDate() %></td>
                                    <td><%= priceHistory.getEndDate() %></td>
                                    <td>
                                        <div class="dropdown">
                                            <button type="button" class="btn p-0 dropdown-toggle hide-arrow"
                                                    data-bs-toggle="dropdown">
                                                <i class="bx bx-dots-vertical-rounded"></i>
                                            </button>
                                            <div class="dropdown-menu">
                                                <a class="dropdown-item" href="price-history-details?id=<%= priceHistory.getId() %>">
                                                    <i class="bx bx-book-content me-1"></i>
                                                    Détails
                                                </a>
                                                <% if (connected) { %>
                                                <a class="dropdown-item" href="form-price-history?action=update&id=<%= priceHistory.getId() %>">
                                                    <i class="bx bx-edit-alt me-1"></i>
                                                    Modifier
                                                </a>
                                                <a class="dropdown-item" href="price-history?action=delete&id=<%= priceHistory.getId() %>">
                                                    <i class="bx bx-trash me-1"></i>
                                                    Supprimer
                                                </a>
                                                <% } %>
                                            </div>
                                        </div>
                                    </td>
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

<%@ include file="footer.jsp" %>
