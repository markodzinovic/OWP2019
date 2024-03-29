$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var filmDP =  document.getElementById('filmDP');
	var datumDP = $('#datumDP');
	var vremeDP = $('#vremeDP');
	var salaDP =  document.getElementById('salaDP');
	var tipDP =  document.getElementById('tipDP');
	var cenaDP = $('#cenaDP');
	
	var porukaParagraf = $('#porukaParagraf');
	
	var dodaj = $('#dodajProjekciju');
	
	$.get('DodavanjeProjekcijeServlet', function(data) {
		
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if(data.status == 'success'){
			
			if(data.uloga == 'KORISNIK'){
				window.location.replace('Glavna.html');
				return;
			}
		
			if(data.uloga == 'ADMIN'){
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
					var vreme = vremeDP.val();
					var sala = salaDP.value;
					var tipProjekcije = tipDP.value;
					var cena = cenaDP.val();
					
					var utc = new Date().toJSON().slice(0,10).replace(/-/g,'-');
					if(utc >= datum){
						porukaParagraf.text('Izabrali ste dan u proslosti ili ne mozete izabrati danasnji dan');
						return;
					}
					
					params = {
							'akcija' : 'dodavanje',
							'film' : film,
							'datum' : datum,
							'vreme' : vreme,
							'sala' : sala,
							'tipProjekcije' : tipProjekcije,
							'cena' : cena
					}
					
					$.post('DodavanjeProjekcijeServlet', params, function(data) {
						console.log(data);
						
						if(data.status == 'failure'){
							porukaParagraf.text(data.poruka);
							return;
						}
						if(data.status == 'success'){
							window.location.replace('Glavna.html');
							return;
						}
					})
				})
			}
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