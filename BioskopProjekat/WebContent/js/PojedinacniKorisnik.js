$(document).ready(function() {
	$('#odjava').on('click', function() {
		$.get('LogoutServlet', function(data) {
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		})
		
	})
	
	var korisnickoImeGore = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(korisnickoImeGore);
	
	function jedanKorisnik () {
		
		var korisni = $('#korisnickoImeP');
		var datum = $('#datumP');
		var tabelaKartiZaKorisnika = $('#tabelaKartiZaKorisnika');
		
		var param = {
			'korisnickoIme' : korisnickoImeGore
		}
		
		$.get('PojedinacniKorisnikServlet', param , function(data) {
			console.log(data)
			
			if(data.status =='unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				console.log(data.uloga)
				
				if(data.uloga == 'KORISNIK'){
					window.location.replace('Glavna.html');
					return;
				}
				if(data.uloga == 'ADMIN'){
					
					var kor = data.korisnik;
					korisni.val(kor.korisnickoIme);
					datum.val(kor.datumRegistracije);
					console.log(kor.uloga)
					console.log(data.sveKorisnickeKarte);
					var karteK = data.sveKorisnickeKarte;
					
					var uloga = document.getElementById("ulogaB");
					uloga.value = kor.uloga;
					
					if(kor.obrisan == true){
						$('#izmenaKorisnika').hide();
						$('#brisanjeKorisnika').hide();
						$('#ulogaB').prop('disabled', true);
						return;
					}
					if(karteK == 0){
						tabelaKartiZaKorisnika.hide();
					}
					admin();
					karteOdKorisnika(tabelaKartiZaKorisnika,karteK)
					return;
				}
				
				
			}
			
			
			
		})
		
	}
	
	function admin () {
		
		$('#izmenaKorisnika').on('click', function() {
			var k = $('#korisnickoImeP');
			var datumR = $('#datumP');
			var u = document.getElementById("ulogaB");
			
			var korisnickoIme = k.val()
			var datumRegistracije = datumR.val()
			var uloga = u.value
			
			
			console.log(korisnickoIme)
			console.log(datumRegistracije)
			console.log(uloga)
			
			 params = {
				'akcija' : 'izmena',
				'korisnickoIme' : korisnickoImeGore,
				'datumRegistracije' : datumRegistracije,
				'uloga' : uloga
			}

			
			$.post('PojedinacniKorisnikServlet', params, function(data) {
				
				if(data.status == 'failure'){
					$('#izmenaKorisnika').hide();
					$('#brisanjeKorisnika').hide();
					$('#ulogaB').prop('disabled', true);
					alert(data.poruka);
					return;
				}
				
				if (data.status == 'success') {
					window.location.replace('Korisnici.html')
					return;
				}
			
				
			})
			
			
		})
		
		$('#brisanjeKorisnika').on('click', function() {
		
		param = {
			'akcija' : 'brisanje',
			'korisnickoIme' : korisnickoImeGore
		}
		$.post('PojedinacniKorisnikServlet', param , function(data) {
			
			if(data.status == 'failure'){
				$('#izmenaKorisnika').hide();
				$('#brisanjeKorisnika').hide();
				$('#ulogaB').prop('disabled', true);
				alert(data.poruka)
				return;
			}
			
			if (data.status == 'success') {
				window.location.replace('Korisnici.html')
				return;
			}
		} )
		
	} )
		
	}
	
	function karteOdKorisnika(tabelaKarti,l1) {
		
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

	jedanKorisnik();
})