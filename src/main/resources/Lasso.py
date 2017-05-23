from __future__ import print_function
import argparse as args
import numpy as np
import pandas as ps
from sklearn.preprocessing import PolynomialFeatures
from sklearn.metrics import r2_score
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
from sklearn.linear_model import Lasso
import sys


def print(s, end='\n', file=sys.stdout):
    file.write(s + end)
    file.flush()


def deg_draw(X, y, degrees, lam, split_value=0.2, iter=10000, save='result_ridge_regression_{}.png'):
    X_train, X_test, y_train, y_test = \
        train_test_split(X, y, test_size=split_value)

    min_x, max_x = np.min(X), np.max(X)

    def draw(degree, weight):
        point = np.linspace(min_x, max_x, 50)
        f_point = PolynomialFeatures(degree=degree) \
            .fit_transform(point.reshape(point.shape[0], 1))
        plt.plot(point, np.dot(f_point, weight), color='red')

    legend = []
    plt.scatter(X, y)
    for l in lam:
        ll = Lasso(alpha=l, max_iter=iter, fit_intercept=False)
        X_poly = PolynomialFeatures(degrees).fit_transform(X_train)
        ll.fit(X=X_poly, y=y_train)
        X_test_poly = PolynomialFeatures(degrees).fit_transform(X_test)
        pred = ll.predict(X_test_poly)
        print("Dergee = {} , alp = {}, Features = {}, Score(R2) = {}".format(degrees, l,
                                                                             np.count_nonzero(ll.coef_),
                                                                             r2_score(y_test, pred)))
        draw(degree=degrees, weight=ll.coef_)
        legend.append("alp = {}".format(l))
    plt.legend(legend, loc='lower center', bbox_to_anchor=[0, 1])
    plt.savefig(save.format(degrees))
    plt.show()


def main():
    argument = args.ArgumentParser(description="Regression")
    argument.add_argument('-data', '--data', dest="data", help='File with dataset into csv')
    argument.add_argument('-degree', '--degree', dest="degree", help='Degree polynomial feature')
    argument.add_argument('-alpha', '--alpha', dest="alpha",
                          help='Complexity parameter that controls the amount of shrinkage')
    argument.add_argument('-iter', '--iter', dest="iter", help='Max iteration')
    argument.add_argument('-split', '--split', dest="split", help='Part of split data')

    parser = argument.parse_args()
    if parser.data is None:
        argument.print_help()
        exit()

    dataset_file = parser.data
    print("use %s file .... " % dataset_file)

    data = ps.read_csv(dataset_file)
    if data.shape[1] != 2:
        print("use 2d point in regression")
        exit()

    if parser.degree is None:
        print("input degree in None")
        exit()

    degree = int(parser.degree)

    if parser.alpha is None:
        print("input alpha param in None")
        exit()

    alpha = float(parser.alpha)

    X = data.drop('y', axis=1).as_matrix()
    y = data['y'].as_matrix()

    split_value = 0.2
    if parser.split is not None:
        split_value = float(parser.split)

    iteration = 100000
    if parser.iter is not None:
        iteration = int(parser.iter)

    deg_draw(X, y, degrees=degree, lam=[alpha], iter=iteration, split_value=split_value)


if __name__ == '__main__':
    main()
