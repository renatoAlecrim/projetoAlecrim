module.controller("FluxoController", ["$scope", "$http", "$routeParams", "$location", "$timeout", "ServicePaginacao", '$rootScope', "ServiceFuncoes", function ($scope, $http, $routeParams, $location, $timeout, ServicePaginacao, $rootScope, ServiceFuncoes) {

        $scope.busca = {};
        $scope.placeHolder = "Buscar Transação";
        $scope.ent = $rootScope.ent = "fluxo";
        $scope.campoPrincipal = 'descricao';

        $scope.atualizarListagens = function (qtdePorPag, pag, campo, string, troca, paro) {
            if (campo == null || campo == "") {
                campo = $scope.campoPrincipal;
            }
            $scope.dadosRecebidos = ServicePaginacao.atualizarListagens(qtdePorPag, pag, campo, string, $rootScope.ent, troca, paro);
            atualizaScope;
        };

        function atualizaScope() {
            $scope = $rootScope;
        }

        $rootScope.atualizarListagens = $scope.atualizarListagens;

        $scope.registrosPadrao = function () {
            $scope.busca.numregistros = ServicePaginacao.registrosPadrao($scope.busca.numregistros);
            $rootScope.numregistros = $scope.busca.numregistros;
        };

        $scope.fazPesquisa = function (registros, string) {
            $rootScope.string = string;
            $scope.atualizarListagens(registros, 1, $scope.campoAtual, string, $rootScope.ent, 0, false);
        };

        function novoFluxo() {
            $scope.fluxo = {
                descricao: "",
                preco: 0,
                pago: false,
                dataentrada: "",
                dataentrega: "",
                cliente: "",
                nomecliente: ""
            };
            $scope.mostrarSelect = false;
            $scope.isNovoFluxo = true;
        }

        $scope.novoFluxo = function () {
            novoFluxo();
            //$("#visualizarnocalendario").val('true');
        };

        $scope.carregarFluxo = function () {
//            console.log($location.path().substring(0, 11));
            if ($location.path().substring(0, 11) === "/Fluxo/novo") {
                novoFluxo();
//                if($routeParams.data){
//                    var d = new Date($routeParams.data);
//                    var dataCerta = new Date(d.getTime() + (d.getTimezoneOffset() * 60000));
//                    $scope.evento.datainicial = dataCerta;
//                    $scope.evento.datafinal = dataCerta;
//                }
            } else {
                $http.get("/transacoes/" + $routeParams.id)
                        .success(function (data) {
                            $scope.fluxo = data[0];
                            $scope.isNovoFluxo = false;
                        })
                        .error(deuErro);
            }
        };

        $scope.atualizarFluxo = function () {
            $http.get("/transacoes")
                    .success(function (data) {
                        $scope.fluxo = data;
                    })
                    .error(deuErro);
        };

        $scope.editarFluxo = function (fluxo) {
            $location.path("/Fluxo/editar/" + fluxo.idfluxo);
        };

        $scope.dateToData = function (valor) {
            var date = new Date(valor);
            date = new Date(date.getTime() + (date.getTimezoneOffset() * 60000));
            var dia = (date.getDate() < 10) ? "0" + date.getDate() : date.getDate();
            var data = dia + "/" + (((date.getMonth() + 1) < 10) ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "/" + date.getFullYear();
            return (valor != null) ? data : "";
        };

        $scope.carregaCliente = function () {

            $http.get("/transacoes/getCliente")
                    .success(function (data) {
                        $scope.clientes = data;
                    })
                    .error(deuErroSalvar);
        };

        $scope.salvarFluxo = function () {

            var dataEntrada = dataToDate($scope.fluxo.dataentrada);
            var dataEntrega = dataToDate($scope.fluxo.dataentrega);
            var fluxoCorreto = {
                descricao: $scope.fluxo.descricao,
                valor: $scope.fluxo.preco,
                pago: $scope.fluxo.pago,
                dataentrada: dataEntrada + "T00:00:00-03",
                dataentrega: dataEntrega + "T00:00:00-03",
                idcliente: ($scope.fluxo.cliente == "" || $scope.fluxo.cliente == undefined) ? null : $scope.fluxo.cliente.idpessoa,
                nomecliente: ($scope.fluxo.nomecliente == undefined) ? "" : $scope.fluxo.nomecliente,
                tipo: ($scope.fluxo.cliente == "" || $scope.fluxo.cliente == undefined) ? null : $scope.fluxo.cliente.tipo_pessoa
            };
            if($scope.mostrarSelect){
                fluxoCorreto.idcliente = null;
            }
            console.log(fluxoCorreto);
            if (new Date(dataEntrada) <= new Date(dataEntrega)) {
                $http.post("/transacoes", fluxoCorreto)
                        .success(function () {
                            $location.path("/Fluxo/listar");
                            toastr.success("Transação inserida com sucesso!");
                        })
                        .error(function (err) {
                            console.log(err);
                            deuErroSalvar;
                        });
            } else {
                toastr.warning("Datas Invalidas");
            }

        };

        $scope.confirmaExclusao = function (entidade, nomeEntidade, nomeRegistro, id) {
            jQuery('#apagarModal').modal('show', {backdrop: 'static'});
            $scope.dadosExclusao = {};
            $scope.dadosExclusao.entidade = entidade;
            $scope.dadosExclusao.nomeEntidade = nomeEntidade;
            $scope.dadosExclusao.nomeRegistro = nomeRegistro;
            $scope.dadosExclusao.id = id;
        };

        $scope.excluiRegistro = function () {
            ServiceFuncoes.excluiRegistro($scope.dadosExclusao.entidade, $scope.dadosExclusao.nomeEntidade, $scope.dadosExclusao.nomeRegistro, $scope.dadosExclusao.id);
            $timeout(function () {
                $scope.atualizarListagens($scope.busca.numregistros, $rootScope.pagina, $scope.campoAtual, '', '', $rootScope.ent, false);
            }, 100);
        };

        function dataToDate(valor) {
            var date = new Date(valor);
            var data = date.getFullYear() + "-" + (date.getMonth() + 1) + '-' + date.getDate();
            return data;
        }

        function deuErro() {
            toastr.error("Algo deu errado. Tente novamente.");
        }

        function deuErroAtualizacao() {
            toastr.error("Atenção, erro ao tentar editar a transação, verifique!");
        }

        function deuErroSalvar() {
            toastr.error("Atenção, erro ao tentar salvar a transação, verifique!");
        }

    }]);