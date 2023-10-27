const imagemInput = document.getElementById('imagemInput');
const imagemPreview = document.getElementById('imagemPreview');

imagemInput.addEventListener('change', function() {
  const arquivo = this.files[0];
  
  if (arquivo) {
    const reader = new FileReader();
    
    reader.addEventListener('load', function() {
      imagemPreview.setAttribute('src', reader.result);
    });
    
    reader.readAsDataURL(arquivo);
  }
});
