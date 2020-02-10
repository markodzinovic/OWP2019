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
	
	var tabelaProjekcije = $('#projekcijaK');
	var sedistaKK = document.getElementById('sedista');
	var odabraoSedista = $('#dalje');
	var tabelaKarte = $('#kartaTabela');
	
	tabelaKarte.hide();
	
	param = {
			'idProjekcije' : idProjekcije
	}
	
	$.get('KupovinaKarteServlet',param, function(data) {

		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if(data.status == 'success'){
			console.log(data)
			console.log(data.ss)
			
			if(data.proslost == 'proslost'){
				$('#sedista').prop('disabled', true);
				$('#dalje').prop('disabled', true);
				alert("Projekcija je u proslosti, molimo vas pogledajte neku drugu projekciju");
				return;
			}
			
			var projekcija = data.projekcija;
			
			tabelaProjekcije.append('<tr>'+
					'<td><a href="PojedinacniFilm.html?id='+ projekcija.nazivFilma.id +'">' + projekcija.nazivFilma.naziv + '</td>'+
					'<td><a href="PojedinacnaProjekcija.html?id='+ projekcija.id +'">' + projekcija.datum + '</td>' +
					'<td>' + projekcija.tipProjekcije.naziv + '</td>' +
					'<td>' + projekcija.sala.naziv + '</td>' +
					'<td>' + projekcija.cena + 'e'  + '</td>' +
			'</tr>')
			
			
	
			slobodnaSedista(sedistaKK,data.ss);
			
			odabraoSedista.on('click', function() {
				
				var sediste = sedistaKK.value;
				
				if(sediste == ''){
					$('#sedista').prop('disabled', true);
					$('#dalje').prop('disabled', true);
					alert("Projekcija je rasprodata");
					return;
				}
				
				$('#sedista').prop('disabled', true);
				$('#dalje').prop('disabled', true);
				
				$('#sedista').find('[value='+sedistaKK.value+']').remove();
				
				tabelaKarte.show();
				
				tabelaKarte.append('<tr>'+
						'<td><a href="PojedinacniFilm.html?id='+ projekcija.nazivFilma.id +'">' + projekcija.nazivFilma.naziv + '</td>'+
						'<td><a href="PojedinacnaProjekcija.html?id='+ projekcija.id +'">' + projekcija.datum + '</td>' +
						'<td>' + projekcija.tipProjekcije.naziv + '</td>' +
						'<td>' + projekcija.sala.naziv + '</td>' +
						'<td>' + projekcija.cena + 'e'  + '</td>' +
						'<td>' + sediste  + '</td>' +
						'<td><input type="submit" value="Kupi kartu" id="kupiKartu"></td>' +
				'</tr>')
				
				
				$('#kupiKartu').on('click', function() {
					params = {
							'idProjekcije' : idProjekcije,
							'sediste' : sediste
						}
						console.log(params)
					
					$.post('KupovinaKarteServlet', params, function(data) {
						console.log(data);
						if(data.status == 'success'){
							window.location.replace('Karte.html');
							return;
						}
					})
				})
				
			})
			
		}
		
		
	})
	
	function slobodnaSedista(s1,l1) {
		var s1 = s1;
		var l1 = l1;
		s1.innerHTML = '';
		
		for ( var i in l1) {
			var novaOpcija = document.createElement('option');
			novaOpcija.value = l1[i];
			novaOpcija.innerHTML = l1[i];
			s1.options.add(novaOpcija);
		}
		
	}
	
	
	
})