$(document).ready(function(){

    clearMessages();
    var carros = [];
    loadCarros();
  });

  function clearMessages(){
    $('#alert-success').hide();
    $('#alert-fail').hide();
    $('#alert-unexpected-fail').hide();
  }

  function executarJob(){

    $.get('http://localhost:8080/api/car/batch', function(mensagem, status){

        clearMessages();

        if('COMPLETED' === mensagem){
            $('#alert-success').show();
        } else if('FAILED' === mensagem){
            $('#alert-fail').show();
        }else{
            $('#alert-unexpected-fail').show();
        }
    });
  }

  function loadCarros(){

    $('#table-carros').find('tbody').each(function(){ this.remove();});

    $.get('http://localhost:8080/api/car', function(result, status){
        
         carros = result;

         $(carros).each(
             
            function(){
              
                var $linha = $('<tr>');
                    var $id = $('<td>');
                    var $renavam = $('<td>');
                    var $brand = $('<td>');
                    var $model = $('<td>');
                    var $yearFabrication = $('<td>');
                    var $modelYear = $('<td>');
                    var $valueBuy = $('<td>');
                    var $valueSale = $('<td>');
                    var $percentMaximumDiscount = $('<td>');
                    var $store = $('<td>');
                    var $dateRegister = $('<td>');

                $id.append(this.id);
                $renavam.append(this.renavam);
                $brand.append(this.brand);
                $model.append(this.model);
                $yearFabrication.append(this.yearFabrication);
                $modelYear.append(this.modelYear);
                $valueBuy.append(this.valueBuy);
                $valueSale.append(this.valueSale);
                $percentMaximumDiscount.append(this.percentMaximumDiscount);
                $store.append(this.store);
                $dateRegister.append(this.dateRegister);

                $linha.append($id);
                $linha.append($renavam);
                $linha.append($brand);
                $linha.append($model);
                $linha.append($yearFabrication);
                $linha.append($modelYear);
                $linha.append($valueBuy);
                $linha.append($valueSale);
                $linha.append($percentMaximumDiscount);
                $linha.append($store);
                $linha.append($dateRegister);

                $tableCarros = $('#table-carros');
                $tableCarros.append($linha);
            })
    });
}