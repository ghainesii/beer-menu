$(document).ready(function() {

    $('.beerName').each(function() {
       if ($(this).text().length > 50) {
               var fontSize = parseFloat($(this).css('font-size')) * 0.6 + 'px';
               $(this).css('font-size', fontSize);
               $(this).css('font-weight', 'bold');
       }
    });

});