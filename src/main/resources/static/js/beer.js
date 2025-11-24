document.addEventListener('DOMContentLoaded', function() {
  const beerNames = document.querySelectorAll('.beerName');

  beerNames.forEach(function(beerName) {
    if (beerName.textContent.length > 50) {
      const currentFontSize = parseFloat(
        window.getComputedStyle(beerName).fontSize
      );
      const newFontSize = currentFontSize * 0.6 + 'px';
      beerName.style.fontSize = newFontSize;
      beerName.style.fontWeight = 'bold';
    }
  });
});