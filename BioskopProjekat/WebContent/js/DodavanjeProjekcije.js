$(document).ready(function() {
	
	var filmDP =  document.getElementById('filmDP');
	var datumDP = $('#datumDP')
	var salaDP =  document.getElementById('salaDP');
	var tipDP =  document.getElementById('tipDP');
	var cenaDP = $('#cenaDP');
	
	var dodaj = $('#dodajProjekciju');
	
	$.get('DodavanjeProjekcijeServlet', function(data) {
		
		console.log(data);
		
		if(data.status == 'success'){
			
			console.log(data.sviFilmovi)
			
			
			var filmovi = data.sviFilmovi;
			var sale = data.sveSale;
			
			filmoviIDNaziv(filmDP, filmovi);
			$('#salaDP').on('change', function (lista) {
				salaPromena(sale);
			})
			
			dodaj.on('click', function() {
				
				var film = filmDP.value;
				var datum = datumDP.val();
				var sala = salaDP.value;
				var tipProjekcije = tipDP.value;
				var cena = cenaDP.val();
				
				params = {
						'akcija' : 'dodavanje',
						'film' : film,
						'datum' : datum,
						'sala' : sala,
						'tipProjekcije' : tipProjekcije,
						'cena' : cena
				}
				
				$.post('DodavanjeProjekcijeServlet', params, function(data) {
					console.log(data);
					
					if(data.status == 'failure'){
						alert(data.poruka);
						return;
					}
					if(data.status == 'success'){
						window.location.replace('Glavna.html');
						return;
					}
				})
			})
			
			
			
		}
		
	})
	
	function filmoviIDNaziv(p1,l1) {
		var p1 = p1;
		var l1 = l1;
		p1.innerHTML = '';
		
		for ( var i in l1) {
			var novaOpcija = document.createElement('option');
			novaOpcija.value = l1[i].id;
			novaOpcija.innerHTML = l1[i].naziv;
			p1.options.add(novaOpcija);
		}
		
	}
	
	function salaPromena(lista) {

		var sala =  document.getElementById('salaDP').value;
		var lista = lista;
		var tip =  document.getElementById('tipDP');
		
		tip.innerHTML = '';
		for(i in lista){
			if(sala == lista[i].id ){
				tipProjekcije = lista[i].tipoviP;
				for(a in tipProjekcije){
					var novaOpcija = document.createElement('option');
					novaOpcija.value = tipProjekcije[a].id;
					novaOpcija.innerHTML = tipProjekcije[a].naziv;
					tip.options.add(novaOpcija);
				}
			}else if(sala == 0){
				return;
			}
		}
	}
})