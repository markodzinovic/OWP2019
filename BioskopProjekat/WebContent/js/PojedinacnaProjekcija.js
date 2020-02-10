$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var idProjekcije = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idProjekcije);
	
	var filmP = $('#filmP');
	var datumP = $('#datumP');
	var tipP = $('#tipP');
	var salaP = $('#salaP');
	var cenaP = $('#cenaP');
	var slobodnaSedistaP = $('#sedistaP');
	
	var porukaParagraf = $('#porukaParagraf');
	
	var kupiKartu = $('#kupiKartuP');
	var adminBrisanje = $('#adminBrisanje');
	var tabelaKartiZaProjekciju = $('#tabelaKartiZaProjekciju');
	
	adminBrisanje.hide();
	tabelaKartiZaProjekciju.hide();
	$('#naslov').hide();
	
	param = {
			'idProjekcije' : idProjekcije
	}
	
	$.get('PojedinacnaProjekcijaServlet',param, function(data) {
		
		console.log(data.status);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if(data.status == 'success'){
			console.log(data.uloga);
			
			console.log(data.projekcija);
			
			var projekcija = data.projekcija;
			
			filmP.val(projekcija.nazivFilma.naziv);
			datumP.val(projekcija.datum);
			tipP.val(projekcija.tipProjekcije.naziv);
			salaP.val(projekcija.sala.naziv);
			cenaP.val(projekcija.cena);
			slobodnaSedistaP.val(data.slobodnaSedista);
			
			if(slobodnaSedistaP.val() == 0){
				kupiKartu.hide();
			}

			if(projekcija.obrisan == true){
				
				if(data.uloga == 'ADMIN'){
					console.log(data.sveKarteZaProjekciju);
					var karte = data.sveKarteZaProjekciju;
					
					kupiKartu.hide();
					adminBrisanje.hide();
					$('#naslov').show();
					tabelaKartiZaProjekciju.show();
					admin();
					karteZaProjek(tabelaKartiZaProjekciju,karte);
					return;
				}
			}
			
			kupiKartu.on('click', function() {
				window.location.replace('KupovinaKarte.html?id='+idProjekcije);
			})

			if(data.uloga == 'ADMIN'){
				console.log(data.sveKarteZaProjekciju);
				var karte = data.sveKarteZaProjekciju;
				
				adminBrisanje.show();
				$('#naslov').show();
				tabelaKartiZaProjekciju.show();
				admin();
				karteZaProjek(tabelaKartiZaProjekciju,karte);
				return;
			}
		}
	})
	
	function admin() {
		adminBrisanje.on('click', function() {
			
			params = {
					'akcija' : 'brisanje',
					'idProjekcije' : idProjekcije
			}
			console.log(idProjekcije);
			$.post('PojedinacnaProjekcijaServlet', params , function(data) {
				console.log(data);
				if(data.status == 'failure'){
					porukaParagraf.text(data.poruka);
					adminBrisanje.hide();
					kupiKartu.hide();
					return;
				}
				if(data.status == 'success'){
					window.location.replace('Glavna.html');
					return;
				}
			})
		})
	}
	
	function karteZaProjek(tabelaKarti,l1) {
		
		for ( k in l1) {
			tabelaKarti.append('<tr>'+
					'<td><a href="PojedinacniFilm.html?id='+l1[k].projekcija.nazivFilma.id+'" >' + l1[k].projekcija.nazivFilma.naziv + '</td>' + 
					'<td><a href="PojedinacnaProjekcija.html?id='+l1[k].projekcija.id+'" >' + l1[k].projekcija.datum + '</td>' +
					'<td><a href="PojedinacnaKarta.html?id='+l1[k].id+'" >' + l1[k].datum + '</td>' +
					'<td>' + l1[k].projekcija.tipProjekcije.naziv + '</td>' +
					'<td>' + l1[k].projekcija.sala.naziv + '</td>' +
					'<td>' + l1[k].sediste + '</td>' +
					'<td>' + l1[k].projekcija.cena + 'e'+ '</td>' +
					'<td><a href="PojedinacniKorisnik.html?id='+l1[k].korisnik.korisnickoIme+'" >' + l1[k].korisnik.korisnickoIme + '</td>' +
			'</tr>')
		}
	}
		
})