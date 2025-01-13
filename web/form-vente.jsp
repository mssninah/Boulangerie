                                                    <td>
                                                        <button type="button" class="btn btn-danger" onclick="removeProductRow(this)">Supprimer</button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <!-- Button to add new rows -->
                                        <button type="button" class="btn btn-primary mt-3" onclick="addProductForm()">Ajouter une ligne</button>

                                        <!-- Submit button -->
                                        <button type="submit" class="btn btn-success mt-3">Enregistrer la vente</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function addProductForm() {
        const productsTableBody = document.getElementById('productsTableBody');
        
        // Create a new row dynamically
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>
                <select name="recipeId[]" class="form-control" required onchange="setUnitPrice(this)">
                    <option value="" disabled selected>Select Recipe</option>
                    <% for (Recipe recipe : recipes) { %>
                        <option value="<%= recipe.getId() %>" data-price="<%= recipe.getPrice() %>">
                            <%= recipe.getTitle() %>
                        </option>
                    <% } %>
                </select>
            </td>
            <td>
                <input type="number" name="unitPrice[]" class="form-control" step="0.01" placeholder="Prix Unitaire" readonly />
            </td>
            <td>
                <input type="number" name="quantity[]" class="form-control" placeholder="Quantité" required oninput="calculateSubtotal(this.closest('tr'))"/>
            </td>
            <td>
                <input type="number" name="subtotal[]" class="form-control" step="0.01" placeholder="Sous-total" readonly />
            </td>
            <td>
                <button type="button" class="btn btn-danger" onclick="removeProductRow(this)">Supprimer</button>
            </td>
        `;
        
        productsTableBody.appendChild(newRow);
    }

    function removeProductRow(button) {
        const row = button.parentElement.parentElement;
        row.remove();
    }

    function setUnitPrice(selectElement) {
        const selectedOption = selectElement.options[selectElement.selectedIndex];
        const price = selectedOption.getAttribute('data-price');
        const unitPriceInput = selectElement.closest('tr').querySelector('input[name="unitPrice[]"]');
        unitPriceInput.value = price;
    }
    function calculateSubtotal(row) {
    const unitPriceInput = row.querySelector('input[name="unitPrice[]"]');
    const quantityInput = row.querySelector('input[name="quantity[]"]');
    const subtotalInput = row.querySelector('input[name="subtotal[]"]');
    
    const unitPrice = parseFloat(unitPriceInput.value) || 0;
    const quantity = parseFloat(quantityInput.value) || 0;

    const subtotal = unitPrice * quantity;
    subtotalInput.value = subtotal.toFixed(2); // Fixe le sous-total avec deux décimales

    updateTotal(); // Mets à jour le total général
}
function updateTotal() {
    const subtotalInputs = document.querySelectorAll('input[name="subtotal[]"]');
    const totalInput = document.getElementById('totalAmount');

    let total = 0;
    subtotalInputs.forEach(input => {
        total += parseFloat(input.value) || 0; // Additionne chaque sous-total
    });

    totalInput.value = total.toFixed(2); // Fixe le total avec deux décimales
}

</script>

<%@include file="footer.jsp"%>
