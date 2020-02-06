
let result = multiply("1020303004875647366210", "2774537626200857473632627613");
console.log(result);

function multiply(a, b) {
  console.log(a, b);
  
  // MULTIPLICATION
  let addition = new Array(0);
  let addTest = new Array(0);
  
  // Pour chaque chiffre du nombre du bas
  for (let i = a.length - 1; i >= 0; i--) {
    let rest = 0;
    let mult = 0;
    let solution = "";
    
    // On compte les dizaines
    let nbZeros = - (i - a.length + 1);
    for (let z = 0; z < nbZeros; z++) {
      solution += "0";
    }
    
    // Pour chaque chiffre du nombre du haut
    for (let j = b.length - 1; j >= 0; j--) {
      let first = parseInt(a.charAt(i), 10);
      let second = parseInt(b.charAt(j), 10);
      mult = first * second;
      
      // Si le chiffre du bas est un 0 on sort de la boucle
      if (first == 0) {
        solution += "0";
        break;
      }

      // Si la multiplication donne plus d'un chiffre
      else if (mult + rest >= 10) {
      
        // On addition avec le reste et on récupère le chiffre tout à droite
        let add = (mult + rest).toString(10).charAt(1);
        solution = add + solution;
        rest = parseInt((mult + rest).toString(10).charAt(0), 10);
        
      // Si le résultat donne un seul chiffre
      } else {
          solution = (mult + rest) + solution;
          rest = 0;
      }
    }
    solution = rest > 0 ? rest + solution : solution;

    addTest.push(solution);

    let splitString = solution.split("");
    let reverseArray = splitString.reverse();
    let joinArray = reverseArray.join("");
    addition.push(joinArray);
  }
  console.log(addTest);
  
  // ADDITION
  let maxLen = 0;
  for (let n = 0; n < addition.length; n++) {
    if (addition[n].length > maxLen) {
      maxLen = addition[n].length;
    }
  }

  solution = "";
  let rest = 0;

  // Pour chaque chiffre du nombre le plus grand
  for (let i = 0; i < maxLen; i++) {
    let add = 0;

    // Pour chaque nombre de la liste
    for (let n = 0; n < addition.length; n++) {
      let num = parseInt(addition[n].charAt(i));
      if (!isNaN(num)) {
        add += num;
      }
    }

    if (add + rest >= 10) {
      let addStr = (add + rest).toString(10);
      solution = addStr.charAt(addStr.length - 1) + solution;
      rest = parseInt(addStr.substring(0, addStr.length - 1));
    } else {
      solution = (add + rest) + solution;
      rest = 0;
    }
  }
  if (rest > 0) {
    solution = rest + solution;
  }
  
  for (let n = 0; n < solution.length; n++) {
    if (solution.charAt(n) != '0') {
      solution = solution.substring(n);
      break;
    }
  }

  return solution;
}