onload = () => {
    const checkbox = document.getElementById('meu-checkbox');
    checkbox.checked = true;
    const checkbox2 = document.getElementById('meu-checkbox2');
    checkbox2.checked = true;
    item1.style.display = 'none';
    item1.style.display = 'block';
    item2.style.display = 'none';
    item2.style.display = 'block';
    
    pc_prject1.style.display = 'block';
    pc_prject2.style.display = 'none';
    pc_prject3.style.display = 'none';

    pc_prject4.style.display = 'block';
    pc_prject5.style.display = 'none';
    pc_prject6.style.display = 'none';
};


function filt(){
  if (item1.value == "Minecraft - nivel basico") {
    pc_prject1.style.display = 'block';
    pc_prject2.style.display = 'none';
    pc_prject3.style.display = 'none';
  }else if (item1.value == "Overwatch - nivel medio") {
    pc_prject1.style.display = 'none';
    pc_prject2.style.display = 'block';
    pc_prject3.style.display = 'none';
  }else if (item1.value == "Cyberpunk - nivel avançado") {
    pc_prject1.style.display = 'none';
    pc_prject2.style.display = 'none';
    pc_prject3.style.display = 'block';
  }
  console.log(item1.value);
};


function filt2(){
  if (item2.value == "Pacote office - nivel basico") {
    pc_prject4.style.display = 'block';
    pc_prject5.style.display = 'none';
    pc_prject6.style.display = 'none';
  }else if (item2.value == "Photoshop - nivel medio") {
    pc_prject4.style.display = 'none';
    pc_prject5.style.display = 'block';
    pc_prject6.style.display = 'none';
  }else if (item2.value == "After efects - nivel avançado") {
    pc_prject4.style.display = 'none';
    pc_prject5.style.display = 'none';
    pc_prject6.style.display = 'block';
  }
  console.log(item2.value);
};





//adiciona um listener para quando o checkbox for clicado
function most(id) {
    // seleciona o elemento checkbox
    const checkbox = document.getElementById("meu-checkbox");
  
    // verifica se o checkbox está marcado
    if (checkbox.checked || id == true) {
      checkbox.checked = true;
        item1.style.display = 'block';
        filt();
      console.log('O checkbox está marcado!');
      id = false;
    } else {
      item1.style.display = 'none';
      pc_prject1.style.display = 'none';
      pc_prject2.style.display = 'none';
      pc_prject3.style.display = 'none';
      console.log('O checkbox não está marcado.');
      id = false;
      most2(true);
    }
};

function most2(id) {
    // seleciona o elemento checkbox
    const checkbox2 = document.getElementById("meu-checkbox2");
  
    // verifica se o checkbox está marcado
    if (checkbox2.checked || id == true) {
      checkbox2.checked = true;
      item2.style.display = 'block';
      filt2();
      console.log('O checkbox está marcado!');
      id = false;
    } else {
      item2.style.display = 'none';
      pc_prject4.style.display = 'none';
      pc_prject5.style.display = 'none';
      pc_prject6.style.display = 'none';

      console.log('O checkbox não está marcado.');
      id = false;
      most(true);
    }
};